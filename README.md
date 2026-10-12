# Bigger AE2

Bigger AE2 是一个面向 Minecraft 1.21.1 NeoForge 的 Applied Energistics 2 扩展，提供更高容量的存储元件、存储单元和合成加速器。

项目地址：<https://github.com/GregTech-Exploring-Pioneer/BiggerAE2>

## 功能

### 合成加速器

提供以下核心数量的合成加速器：

- 4 Core
- 16 Core
- 64 Core
- 256 Core
- 1024 Core

每一级都可以由上一级合成。

### 高容量存储单元

- Quantum Item Storage Cell
- Quantum Fluid Storage Cell
- Digital Singularity Item Storage Cell
- Digital Singularity Fluid Storage Cell

Digital Singularity Storage Cell 使用不可变的无符号大数保存数量，数量不受 `long` 上限限制；对 AE2 API 暴露数量时仍会饱和到 `Long.MAX_VALUE`。

## 环境

- Minecraft 1.21.1
- NeoForge 21.1.218
- Applied Energistics 2
- Java 21

## 构建

Windows：

```bash
./gradlew.bat build
```

Linux/macOS 或 Git Bash：

```bash
./gradlew build
```

构建产物位于 `build/libs/`。

## 大数存储实现

生产代码使用 `UnsignedLongArray` 替代 `BigInteger`：

```text
src/main/java/cn/dancingsnow/bigger_ae2/util/UnsignedLongArray.java
```

实现特点：

- 小端序 `long[]` limb
- 只允许非负数
- 不可变，输入数组和输出数组均会复制
- 支持大数加法、减法、比较和十进制转换
- 加减操作使用无符号进位和借位
- 减法下溢会抛出异常，不会静默回绕

### 旧数据迁移

`DigitalSingularityStorage` 的组件 ID 保持不变：

```text
bigger_ae2:singularity_storage
```

持久化 Codec 同时支持旧的十进制字符串格式和新的 limb 数组格式：

```json
{
  "count": "12345678901234567890"
}
```

读取后重新保存时会写成新的 limb 数组格式。这样可以覆盖已有的 ItemStack，不要求同一 Minecraft 数据版本内额外注入 Vanilla DFU。

网络同步使用新的格式：

```text
可选 AEKey
limb 数量 VarInt
多个 64 位 long limb
```

因此客户端和服务端需要使用相同版本的 mod。

## JMH 性能基准

基准源码：

```text
src/jmh/java/cn/dancingsnow/bigger_ae2/benchmark/UnsignedLongArrayBenchmark.java
```

覆盖以下场景：

- `long` 增量加法和减法
- 多 limb 大数加法和减法
- 连续不可变加法链和减法链
- 普通计算、连续进位、连续借位
- GC 分配量

### 运行基准

快速验证：

```bash
./gradlew jmh -PjmhQuick
```

快速配置使用 1 次预热、1 次测量和 1 个 fork，适合本地检查实现是否正确以及快速观察趋势。

正式配置：

```bash
./gradlew jmh
```

正式配置使用 5 次预热、5 次测量和 2 个 fork，结果会写入：

```text
build/results/jmh/results.json
```

### 正式基准结果

正式基准命令：

```bash
./gradlew jmh
```

测试环境和配置：

- JMH 1.36
- JDK 21.0.4 / OpenJDK 64-Bit Server VM
- 单线程
- 5 次预热，每次 2 秒
- 5 次测量，每次 2 秒
- 2 个 fork
- GC profiler
- 共 144 个测试组合：12 个操作 × 4 种 limb 数量 × 3 种模式

完整 JSON 结果位于：

```text
build/results/jmh/results.json
```

以下是普通计算模式的正式结果，单位为 `ns/op`：

| 操作 | 1 limb：UnsignedLongArray | 1 limb：BigInteger | 32 limbs：UnsignedLongArray | 32 limbs：BigInteger |
| --- | ---: | ---: | ---: | ---: |
| `add(long)` | 2.043 | 4.921 | 12.523 | 14.521 |
| `subtract(long)` | 1.663 | 6.202 | 12.887 | 13.927 |
| `add(big number)` | 2.144 | 5.282 | 13.963 | 29.813 |
| `subtract(big number)` | 2.521 | 6.841 | 20.941 | 30.379 |
| 连续加法链 | 36.516 | 80.307 | 315.827 | 550.453 |
| 连续减法链 | 54.735 | 101.615 | 506.365 | 513.243 |

32 limbs 下的进位和借位结果：

| 操作 | 进位：UnsignedLongArray | 进位：BigInteger | 借位：UnsignedLongArray | 借位：BigInteger |
| --- | ---: | ---: | ---: | ---: |
| `add(long)` | 33.595 | 37.733 | 11.691 | 13.496 |
| `subtract(long)` | 12.812 | 15.281 | 13.996 | 22.365 |
| `add(big number)` | 29.380 | 42.293 | 14.331 | 29.348 |
| `subtract(big number)` | 21.109 | 30.384 | 34.328 | 42.715 |
| 连续加法链 | 258.815 | 489.025 | 228.060 | 594.690 |
| 连续减法链 | 410.141 | 506.125 | 372.051 | 458.087 |

在这次正式基准中，`UnsignedLongArray` 在普通加减、`long` 增量操作和连续更新场景整体优于 `BigInteger`。具体收益会随 limb 数量、进位/借位比例、JDK 和硬件变化。

## 验证

项目构建验证：

```bash
./gradlew build
```

大数实现还使用随机生成的非负 `BigInteger` 结果进行了加法、减法、十进制解析和十进制输出对照验证。

## 许可证

MIT License

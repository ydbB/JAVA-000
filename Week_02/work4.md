9## GC 分析
512m: 并行GC < G1GC < 串行GC ~ CMS GC
1g: 并行GC ~ G1 GC ~ 串行GC ~ CMS GC
4g: 串行GC < CMS GC < 并行GC < G1GC

从整体看，当堆内存较小时，CMS 的 GC方式具有一定优势，当内存在 1g左右时，几种方式的效果相近（基于本机生成对象估算）；当内存达到4g时，G1 的 GC 方式优势较大。

可以从 work1 的截图中得到，并行 GC 在内存较小时，会频繁触发 fullGC，old的利用率会达到 90%左右，随着内存增大，GC 效果会逐渐提升；串行GC的性能并不会随着内存增大而显著提升；CMS GC 的性能会随着内存增大先提升然后下降；G1 GC 的性能会随着内存增加有较大的提升，G1 在内存较小时会频发触发 G1 pause。 
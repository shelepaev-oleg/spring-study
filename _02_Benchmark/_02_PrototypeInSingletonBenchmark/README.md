Пример микробенчмарка для запуска Spring-а, 
сравнивающий два варианта написания паттерна "Prototype в Singleton" (правильный и неправельный варианты)

Алгоритм запуска:
1: `mvn clean install`
2: `java -jar target/benchmarks.jar`

Результат:
Benchmark                                Mode  Cnt  Score   Error  Units
MainBenchmark.prototypeInSingletonOk       ss   10  0,245 ? 0,029   s/op
MainBenchmark.prototypeInSingletonWrong    ss   10  0,277 ? 0,114   s/op
Чем меньше Score, тем лучше

Работает только на Java 1.8,
т.к. пример взят с репозитория https://github.com/dsyer/spring-boot-startup-bench 
,то нужно там смотреть переход на более свежую версию Java


Режимы работы JMH:
https://stackoverflow.com/questions/24928922/jmh-what-does-the-score-value-mean
JMH supports the following Benchmark modes:

Throughput: operations per unit of time
AverageTime: average time per operation
SampleTime: samples the time for each operation
SingleShotTime: measures the time for a single operation
@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import kotlin.math.min
import kotlin.math.sqrt
import kotlin.math.*
/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var k = n
    var count = 0
    if (n == 0) return 1
    while (k > 0) {
        k /= 10
        count++
    }
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var num = 2
    var last = 1
    var s = 0
    when {
        n > 3 -> {
            for (i in 4..n) {
                s = last
                last = num
                num += s
            }
        }
        n == 3 -> num = 2
        n in 1..2 -> num = 1
    }
    return num
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var k = 1
    do {
        if ((k % m == 0) && (k % n == 0)) break
        k++
    } while (k != 1)
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var minDiv = 2
    if (n == 2) return 2
    do {
        if (n % minDiv == 0) return minDiv else minDiv++
    } while (n >= minDiv)
    return minDiv
}


/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var maxDiv = n - 1
    if (n == 2) return 2
    do {
        if (n % maxDiv == 0) return maxDiv else maxDiv--
    } while (0 < maxDiv)
    return maxDiv
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var i = 1
    var div = 2
    while (div <= min(m, n)) {
        if ((m % div == 0) && (n % div == 0)) {
            i++
            break
        }
        div++
    }
    return i == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    for (i in 1..sqrt(n.toDouble()).toInt()) {
        if (i * i in m..n) return true
    }
    return false
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var k = x
    var count = 0
    while (k != 1) {
        count++
        if (k % 2.0 == 0.0) k /= 2 else k = k * 3 + 1
    }
    return count
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var n = x % (2 * PI)
    val newX = n
    val a = -1.0
    var count = 1.0
    var i = 3
    while ((abs(newX.pow(i) / factorial(i) / 1.0)) >= eps) {
        n += a.pow(count) * newX.pow(i) / factorial(i)
        count++
        i += 2
    }
    return n
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var n = 1.0
    val newX = x % (2 * PI)
    val a = -1.0
    var count = 1.0
    var i = 2
    while ((abs(newX.pow(i) / factorial(i) / 1.0)) >= eps) {
        n += a.pow(count) * newX.pow(i) / factorial(i)
        count++
        i += 2
    }
    return n
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var mod = n
    var result = 0
    while (mod > 0) {
        result = (result + mod % 10) * 10
        mod /= 10
    }
    return result / 10
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var mod = n
    var result = 0
    while (mod > 0) {
        result = (result + mod % 10) * 10
        mod /= 10
    }
    result /= 10
    return result == n
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var old = n
    var count = 0
    if (old < 10) return false
    while (old > 10) {
        if (old % 10 != (old % 100) / 10) {
            count += 1
            break
        }
        old /= 10
    }
    return (count == 1)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var x = n
    var result = -1
    var count = 0
    var factor = 0
    when (x) {
        in 1..3 -> result = x * x
        in 4..15 -> {
            count = x - 3
            factor = 4
            while (count > 0) {
                when (count) {
                    1 -> result = (factor * factor) / 10
                    2 -> result = (factor * factor) % 10
                    else -> count -= 2
                }
                factor++
                if (result != -1) break
            }
        }
        in 16..100 -> {
            count = x - 15
            factor = 10
            while (count > 0) {
                when (count) {
                    1 -> result = (factor * factor) / 100
                    2 -> result = ((factor * factor) % 100) / 10
                    3 -> result = (factor * factor) % 10
                    else -> count -= 3
                }
                if (result != -1) break
                factor++
            }
        }
    }

    return result
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var x = n
    var fact = 7
    var factor = 12
    var result = 0
    var count = n
    when (x) {
        in 1..6 -> result = fib(x)
        in 7..16 -> {
            count -= 6
            while (count >= 2) {
                fact++
                count -= 2
            }
            result = if (count == 0) fib(n = fact - 1) % 10 else fib(fact) / 10
        }
        else -> {
            count -= 16
            while (count > 3) {
                factor++
                count -= 3
            }
            result = when (count) {
                1 -> fib(factor) / 100
                2 -> (fib(factor) / 10) % 10
                else -> fib(factor) % 10
            }
        }
    }
    return result
}

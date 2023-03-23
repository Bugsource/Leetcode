package Stack;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

class AsteroidCollision {
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < asteroids.length; ++i) {
            int currAsteroid = asteroids[i];
            if (currAsteroid > 0) {
                // 向右的行星，不可能和之前的发生碰撞，直接入栈
                deque.addLast(currAsteroid);
            } else {
                // 这里的逻辑可以优化为，循环里判断如果栈顶是正行星并且绝对值小于负行星，则直接弹出；
                // 接着判断当前的负行星，如果栈为空，则直接入栈；如果栈顶正行星绝对值和负行星相等，则正行星出栈；如果大于，则什么都不做；
                // 这样可以避免bool变量和break，不过逻辑有点晦涩
                boolean isCurrExploded = false;
                while (!deque.isEmpty() && deque.peekLast() > 0) {
                    // 如果存活在栈顶的是负行星，则不需要考虑，直接入栈
                    int prevAsteroid = deque.peekLast();
                    int sum = prevAsteroid + currAsteroid;
                    if (sum == 0) {
                        // 栈顶正行星和当前负行星爆炸，退出循环
                        deque.pollLast();
                        isCurrExploded = true;
                        break;
                    } else if (sum < 0) {
                        // 栈顶的正行星爆炸
                        deque.pollLast();
                    } else {
                        // 当前负行星爆炸，退出循环
                        isCurrExploded = true;
                        break;
                    }
                }
                if (!isCurrExploded) {
                    deque.addLast(currAsteroid);
                }
            }
        }
        return deque.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        int[] input1 = new int[]{1, 5, -10};
        AsteroidCollision asteroidCollision = new AsteroidCollision();
        int[] res = asteroidCollision.asteroidCollision(input1);
        System.out.println(Arrays.toString(res));

        int[] input2 = new int[]{-2, -2, 1, -2};
        int[] res2 = asteroidCollision.asteroidCollision(input2);
        System.out.println(Arrays.toString(res2));

    }
}

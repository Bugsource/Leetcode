public class EditDistance {

    public int minDistance0(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] cost = new int[m + 1][n + 1];
        for(int i = 0; i < m + 1; ++ i) {
            // word2长度为0，那么有两种方法完成变换：word2经过i次插入，word1经过i次删除；这二者是对偶的
            cost[i][0] = i;
        }
        for(int i = 0; i < n + 1; ++ i) {
            // word1长度为0，那么有两种方法完成变换：word1经过i次插入，word2经过i次删除；这二者是对偶的
            cost[0][i] = i;
        }

        for(int i = 1; i < m + 1; ++ i) {
            for(int j = 1; j < n + 1; ++ j) {
                if(word1.charAt(i-1) == word2.charAt(j-1)) {
                    cost[i][j] = cost[i-1][j-1];
                } else {
                    // word1第i个字符和word2第j个字符不相等，则有以下办法可以完成变换：
                    // 1. 第i个字符替换成第j个字符: cost[i][j] = cost[i-1][j-1] + 1
                    // 2. 第i个字符后插入一个字符，和第j个字符一致: cost[i][j] = cost[i][j-1] + 1(和删除第j个字符对偶)
                    // 3. 删除第i个字符: cost[i][j] = cost[i-1][j] + 1
                    cost[i][j] = Math.min(cost[i-1][j-1], Math.min(cost[i][j-1], cost[i-1][j])) + 1;
                }
            }
        }

        return cost[m][n];
    }

    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[] cost = new int[n + 1];
        for(int j = 0; j < n + 1; ++ j) {
            // 第0行，word1长度为0
            cost[j] = j;
        }

        for(int i = 1; i < m + 1; ++ i) {
            cost[0] = i; // 第i行第0列
            int prev = cost[0];
            for(int j = 1; j < n + 1; ++ j) {
                int temp = cost[j]; // 在第i-1行第j列更新为第i行第j列之前，缓存其值
                if(word1.charAt(i-1) == word2.charAt(j-1)) {
                    cost[j] = prev;
                } else {
                    // word1第i个字符和word2第j个字符不相等，则有以下办法可以完成变换：
                    // 1. 第i个字符替换成第j个字符: cost[i][j] = cost[i-1][j-1] + 1
                    // 2. 第i个字符后插入一个字符，和第j个字符一致: cost[i][j] = cost[i][j-1] + 1(和删除第j个字符对偶)
                    // 3. 删除第i个字符: cost[i][j] = cost[i-1][j] + 1
                    cost[j] = Math.min(prev, Math.min(cost[j-1], cost[j])) + 1;
                }
                prev = temp;
            }
        }

        return cost[n];
    }
}

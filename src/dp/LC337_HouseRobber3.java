package dp;



public class LC337_HouseRobber3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 限制：不能连续偷父子节点。
     */
    public int rob(TreeNode root) {
//        int[] dp = robHelper(root);
//        return Math.max(dp[0], dp[1]);
        DPRes dpRes = robHelper(root);
        return Math.max(dpRes.rob, dpRes.notRob);
    }

    private record DPRes(int rob, int notRob){}

    // 使用record类提高可读性
    private DPRes robHelper(TreeNode root) {
        if(root == null) {
            return new DPRes(0, 0);
        }
        DPRes leftChild = robHelper(root.left);
        DPRes rightChild = robHelper(root.right);

        // 当前选，子节点不能选，那么需要把子节点不选的结果加上
        int currRob = root.val + leftChild.notRob + rightChild.notRob;
        // 当前不选，那么子节点可以选，应该取子节点选和不选的最大值
        int currNotRob = Math.max(leftChild.rob, leftChild.notRob) + Math.max(rightChild.rob, rightChild.notRob);

        return new DPRes(currRob, currNotRob);
    }

    private int[] robHelperV0(TreeNode root) {
        if(root == null) {
            return new int[] {0,0};
        }
        int[] leftChild = robHelperV0(root.left);
        int[] rightChild = robHelperV0(root.right);

        // 关键点，第一个元素存储的是当前节点选的情况下的最大值；第二个元素是不选的情况下最大值
        int[] currRes = new int[2];
        // 当前选，子节点不能选，那么需要把子节点不选的结果加上
        currRes[0] = root.val + leftChild[1] + rightChild[1];
        // 当前不选，那么子节点可以选，应该取子节点选和不选的最大值
        currRes[1] = Math.max(leftChild[0], leftChild[1]) + Math.max(rightChild[0], rightChild[1]);

        return currRes;
    }
}

package Stack;

import java.util.ArrayDeque;
import java.util.Deque;

import static util.Util.assertTrue;

public class LC71M_SimplifyPath {

    /**
     * 输出的简化路径，不能含有".."和"."；而"..."是合法路径名。不能含有多个"/"。
     * <p>
     * 其实文件夹主要操作，就是跳出当前文件夹，和进入下一个文件夹，对应出栈和入栈。
     * <p>
     * 用"/"分割，可以解决多个/的问题，将精力聚焦在文件夹的跳进跳出；结果再拼上就好。
     * 多个 /‌：会产生多个空字符串 ""；开头 /‌：产生一个前导空字符串 ""；所以要判断是否为空字符串。
     */
    public static String simplifyPath(String path) {
        Deque<String> deque = new ArrayDeque<>();
        String[] directories = path.split("/");
        for(String directory : directories) {
            if(directory.equals("..")) {
                if(!deque.isEmpty()) {
                    deque.pollLast();
                }
            } else if(!directory.isEmpty() && !directory.equals(".")) {
                deque.offerLast(directory);
            }
        }
        if(deque.isEmpty()) {
            return "/";
        }
        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()) {
            sb.append("/").append(deque.pollFirst());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s1 = "/.../a/../b/c/../d/./";
        System.out.println("res1:" + simplifyPath(s1));
    }
}

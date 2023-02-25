import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Twitter {

    private Map<Integer, Integer> userToTweet;
    private Map<Integer, Set<Integer>> userToFollowee;

    public Twitter() {
        userToTweet = new HashMap<>();
        userToFollowee = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {

    }

    public List<Integer> getNewsFeed(int userId) {
        return new ArrayList<>();
    }

    public void follow(int followerId, int followeeId) {
        userToFollowee.computeIfAbsent(followerId, key -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        userToFollowee.computeIfAbsent(followerId, key -> new HashSet<>()).remove(followeeId);
    }
}

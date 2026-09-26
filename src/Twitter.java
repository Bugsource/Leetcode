import LinkedList.LC146_LRU;

import java.util.*;

/**
 * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and is able to see the 10 most recent tweets in the user's news feed.
 * <p>
 * Implement the Twitter class:
 * <p>
 * Twitter() Initializes your twitter object.
 * void postTweet(int userId, int tweetId) Composes a new tweet with ID tweetId by the user userId.
 * Each call to this function will be made with a unique tweetId.
 * <p>
 * List<Integer> getNewsFeed(int userId) Retrieves the 10 most recent tweet IDs in the user's news feed.
 * Each item in the news feed must be posted by users who the user followed or by the user themself.
 * Tweets must be ordered from most recent to least recent.
 * <p>
 * void follow(int followerId, int followeeId) The user with ID followerId started following the user with ID followeeId.
 * <p>
 * void unfollow(int followerId, int followeeId) The user with ID followerId started unfollowing the user with ID followeeId.
 */
public class Twitter {
    private static class Twitt {
        int tweetId;
        Twitt pre;
        Twitt next;

        Twitt(int tweetId) {
            this.tweetId = tweetId;
            pre = null;
            next = null;
        }
    }

    private static class Feeds {
        Twitt head;
        Twitt tail;
        int pageSize;
        // 对于一个用户发出的帖子，他的关注者都需要深拷贝一份：如果使用浅拷贝，在不同双向链表里会引用同一个节点，会产生数据写竞争，双向链表脱节
        // tweetId是唯一的，所以可以用来做key
        Map<Integer, Twitt> idToTwittCache;

        Feeds() {
            head = new Twitt(0);
            tail = new Twitt(0);
            idToTwittCache = new HashMap<>();
            pageSize = 10;

            head.next = tail;
            head.pre = null;
            tail.pre = head;
            tail.next = null;
        }

        public List<Integer> getLatestTwitts() {
            List<Integer> res = new ArrayList<>();
            Twitt curr = head.next;
            int i = 0;
            while(curr != tail && i < pageSize) {
                res.add(curr.tweetId);
                curr = curr.next;
                ++ i;
            }
            return res;
        }

        public void addToHead(Twitt twitt) {
            Twitt temp = head.next;

            head.next = twitt;
            twitt.pre = head;

            twitt.next = temp;
            temp.pre = twitt;

            idToTwittCache.put(twitt.tweetId, twitt);
        }

        public void remove(Integer tweetId) {
            Twitt twitt = idToTwittCache.get(tweetId);
            if(twitt == null) {
                return;
            }
            Twitt pre = twitt.pre;
            Twitt next = twitt.next;

            pre.next = next;
            next.pre = pre;
        }
    }

    // 收件箱（时间线）
    private final Map<Integer, Feeds> userToFeeds;
//    private final Map<Integer, Set<Integer>> userToFollowees;

    // 发件箱
    private final Map<Integer, List<Twitt>> userToTwitts;

    private final Map<Integer, Set<Integer>> userToFollowers;

    public Twitter() {
        userToFeeds = new HashMap<>();
        userToFollowers = new HashMap<>();
        userToTwitts = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        Twitt twitt = new Twitt(tweetId);
        userToTwitts.computeIfAbsent(userId, key -> new ArrayList<>()).add(twitt);

        // 获取 followers，如果为 null 则视为空集合，避免 NPE
        Set<Integer> followers = userToFollowers.getOrDefault(userId, Collections.emptySet());

        // 创建包含 userId 和所有 followers 的新集合；因为自己的时间线，也要能看到自己发的帖子，
        Set<Integer> diffusionTargets = new HashSet<>(followers);
        diffusionTargets.add(userId);

        diffusionTargets.forEach(follower -> {
            // 深拷贝一份，插入每个关注者以及自己的收件箱（时间线）的头部
            Twitt deepCopy = new Twitt(tweetId);
            userToFeeds.computeIfAbsent(follower, key -> new Feeds()).addToHead(deepCopy);
        });
    }

    public List<Integer> getNewsFeed(int userId) {
        Feeds feeds = userToFeeds.get(userId);
        if(feeds == null) {
            return new ArrayList<>();
        } else {
            return feeds.getLatestTwitts();
        }
    }

    public void follow(int followerId, int followeeId) {
//        userToFollowees.computeIfAbsent(followerId, key -> new HashSet<>()).add(followeeId);
        userToFollowers.computeIfAbsent(followeeId, key -> new HashSet<>()).add(followerId);
    }

    public void unfollow(int followerId, int followeeId) {
//        userToFollowees.computeIfAbsent(followerId, key -> new HashSet<>()).remove(followeeId);
        Set<Integer> followers = userToFollowers.get(followeeId);

        if(followers == null || followers.isEmpty()) {
            // should throw exception
            return;
        }
        // 删除关注者id
        followers.remove(followerId);

        List<Twitt> twittsByFollowee = userToTwitts.get(followeeId);
        // 被关注者发过的帖子集合不为空
        if(twittsByFollowee != null && !twittsByFollowee.isEmpty()) {
            // 删除当前关注者的时间线里，被关注者发布过的帖子
            Feeds feeds = userToFeeds.get(followerId);
            if(feeds != null) {
                twittsByFollowee.stream().map(t -> t.tweetId).forEach(feeds::remove);
            }
        }
    }

    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 5);
        System.out.println("getNewsFeed:" + twitter.getNewsFeed(1));

        twitter.follow(1, 2);
        twitter.postTweet(2, 6);

        System.out.println("getNewsFeed:" + twitter.getNewsFeed(1));

        twitter.unfollow(1, 2);
        System.out.println("getNewsFeed:" + twitter.getNewsFeed(1));
    }
}

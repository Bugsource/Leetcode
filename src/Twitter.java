import LinkedList.LC146_LRU;

import java.util.*;
import java.util.stream.Collectors;

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
    // 全局自增时间戳，只适用于单机；分布式环境应该与分布式唯一id取代
    int timeStamp = 0;

    private static class Twitt {
        int tweetId;
        int timeStamp;

        Twitt(int tweetId, int timeStamp) {
            this.tweetId = tweetId;
            this.timeStamp = timeStamp;
        }
    }

    private static class Feeds {
        int pageSize;

        TreeMap<Integer, Twitt> timeToTwittSortedMap;

        Feeds() {
            timeToTwittSortedMap = new TreeMap<>();
            pageSize = 10;
        }

        public List<Integer> getLatestTwitts() {
            return timeToTwittSortedMap.descendingMap().entrySet()
                    .stream().limit(pageSize)
                    .map(entry -> entry.getValue().tweetId)
                    .toList();
        }

        public void add(Twitt twitt) {
            timeToTwittSortedMap.put(twitt.timeStamp, twitt);
        }

        public void addTwitts(List<Twitt> twitts) {
            twitts.forEach(this::add);
        }

        public void remove(Twitt twitt) {
            timeToTwittSortedMap.remove(twitt.timeStamp);
        }
    }

    // 收件箱（时间线）
    private final Map<Integer, Feeds> userToFeeds;

    // 发件箱
    private final Map<Integer, List<Twitt>> userToTwitts;

    private final Map<Integer, Set<Integer>> userToFollowers;

    public Twitter() {
        userToFeeds = new HashMap<>();
        userToFollowers = new HashMap<>();
        userToTwitts = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        int currTimeStamp = ++timeStamp;
        Twitt twitt = new Twitt(tweetId, currTimeStamp);
        userToTwitts.computeIfAbsent(userId, key -> new ArrayList<>()).add(twitt);

        // 获取 followers，如果为 null 则视为空集合，避免 NPE
        Set<Integer> followers = userToFollowers.getOrDefault(userId, Collections.emptySet());

        // 创建包含 userId 和所有 followers 的新集合；因为自己的时间线，也要能看到自己发的帖子，
        Set<Integer> diffusionTargets = new HashSet<>(followers);
        diffusionTargets.add(userId);

        diffusionTargets.forEach(follower -> {
            // 插入每个关注者以及自己的收件箱（时间线）
            userToFeeds.computeIfAbsent(follower, key -> new Feeds()).add(twitt);
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
        userToFollowers.computeIfAbsent(followeeId, key -> new HashSet<>()).add(followerId);
        // 需要将被关注对象的所有帖子，插入到关注者的Feeds流（收件箱）；按照时间戳排序；
        // 所以双端队列并不适用，因为并不总是插入到头部
        List<Twitt> twittsByFollowee = userToTwitts.get(followeeId);
        // 被关注者发过的帖子集合不为空
        if(twittsByFollowee != null && !twittsByFollowee.isEmpty()) {
            userToFeeds.computeIfAbsent(followerId, key -> new Feeds()).addTwitts(twittsByFollowee);
        }
    }

    public void unfollow(int followerId, int followeeId) {
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
                twittsByFollowee.forEach(feeds::remove);
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("testcase1==================");

        Twitter twitter = new Twitter();
        twitter.postTweet(1, 5);
        System.out.println("getNewsFeed:" + twitter.getNewsFeed(1));

        twitter.follow(1, 2);
        twitter.postTweet(2, 6);

        System.out.println("getNewsFeed:" + twitter.getNewsFeed(1));

        twitter.unfollow(1, 2);
        System.out.println("getNewsFeed:" + twitter.getNewsFeed(1));

        System.out.println("testcase2==================");
        Twitter twitter2 = new Twitter();
        twitter2.postTweet(1, 1);
        System.out.println("getNewsFeed for user 1:" + twitter2.getNewsFeed(1));

        twitter2.follow(2, 1);

        System.out.println("getNewsFeed for user 2:" + twitter2.getNewsFeed(2));

        twitter2.unfollow(2, 1);
        System.out.println("getNewsFeed for user 2:" + twitter2.getNewsFeed(2));
    }
}

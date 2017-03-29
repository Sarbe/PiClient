package com.pi.feed;

import com.pi.util.CommonFunction;


public class ReadTest {
	public static Feed fetchFeeds() {
		RSSFeedParser parser = new RSSFeedParser(
				"http://timesofindia.indiatimes.com/rssfeedstopstories.cms");
		Feed feed = null;
		try {
			feed = parser.readFeed();
			//System.out.println(feed);
			for (FeedMessage message : feed.getMessages()) {
				//System.out.println(message);
				message.setImgLink(CommonFunction.getImgFromLink(message.link));
				//System.out.println(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feed;
	}
}

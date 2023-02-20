package wolfPub;

public class arjun_trial {
	public static void main(String[] args) {
		//Chapter javaPos = new Chapter(11,"chapter11",5,-1);
		
		//works
		Chapter c = Chapter.getChapter(11,5);
		System.out.println(c.getChapterName());
		System.out.println(c.getChapterNumber());
		System.out.println(c.getChapterBook().getBookTitle());
		
		//returnedObj.updateBook("book3",2,1,"this is book1 updated","0000-00-00", -1);
	
		//int id  = Publication.getPublicationIdByTitle("publication_1");
		//System.out.println(id);
		
		//update needs to be clarified
		//Publication returnobj2 = Publication.getPublicationById(3);
		//returnobj2.removePublication(-1) ;
		//System.out.println(returnobj2.getPublicationId());
		//System.out.println(returnobj2.getPublicationTitle());
		//System.out.println(returnobj2.getPrice());
		//System.out.println(returnobj2.getPublicationTopic());
		
		
//		System.out.println(returnedObj.getIssueTitle());
//		System.out.println(returnedObj.getIssuePublication().getPublicationTitle());
//		System.out.println(returnedObj.getIssueCategory().getCategoryName());
//		System.out.println(returnedObj.getIssuePeriodicity().getPeriodicityName());
		
		//System.out.println(returnedObj2.getTopicId());
		//System.out.println(returnedObj2.getTopicName());
		
		//Audience audience = new Audience("aud111",-1);
		c.updateChapter( "chapterr", -1);
		
		//returnedObj.removePosition(-1);
	}
}

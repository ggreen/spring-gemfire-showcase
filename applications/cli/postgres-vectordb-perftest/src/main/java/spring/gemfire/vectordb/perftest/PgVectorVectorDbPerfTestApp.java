package spring.gemfire.vectordb.perftest;

import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.operations.performance.BenchMarker;
import nyla.solutions.core.operations.performance.PerformanceCheck;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class PgVectorVectorDbPerfTestApp {

	@Value("${statsCapacity:300}")
	private int statsCapacity;

	@Value("${query:I love Spring}")
	private String query ;


	@Value("${warmCount:10}")
	private Long warmCount;

	@Value("${loopCount:100}")
	private Long loopCount;

	@Value("${threshold:.7}")
	private double threshold;

	@Value("${topK;:3}")
	private int topK;

	public static void main(String[] args) {
		new SpringApplicationBuilder(PgVectorVectorDbPerfTestApp.class)
				.web(WebApplicationType.NONE) // Key change: explicitly sets application type to non-web
				.run(args);
	}

	@Bean
	List<Document> docs()
	{
		return List.of(new Document("I love Spring AI"),
				new Document("I love Spring Cloud Stream"),
				new Document("I love Spring Data"),
				new Document("I love JUNIT"),
				new Document("I love Nyla Solutions"));
	}

	@Bean
	SearchRequest searchRequest()
	{
		return SearchRequest.builder().similarityThreshold(threshold)
				.query(query).topK(topK).build();
	}
	@Bean
	CommandLineRunner runner(VectorStore vectorStore, List<Document> docs,
							 SearchRequest searchRequest)
	{
		return  args-> {

			log.info("Setting up performance test, for query:{}",query);
			//save docs
			var hasResults= vectorStore.similaritySearch(query);
			if(hasResults.isEmpty())
			{
				log.info("Adding docs: {}",docs);
				vectorStore.add(docs);
			}


			log.info("warmCount: {}",warmCount);
			for (int i=0;i < warmCount; i++)
				vectorStore.similaritySearch(searchRequest);

			//Start PerformanceTest
			log.info("Performance with loop count: {} searchQuest: {}",loopCount,searchRequest);
			var perfTest = new PerformanceCheck(BenchMarker.builder().loopCount(loopCount)
					.threadCount(1).build(),
					statsCapacity);

			perfTest.perfCheck(() ->{
				vectorStore.similaritySearch(searchRequest);

			});

			log.info("Report: {}",perfTest.getReport());
		};
	}
}

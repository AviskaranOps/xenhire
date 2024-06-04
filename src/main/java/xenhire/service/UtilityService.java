package xenhire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xenhire.dto.CandidateValuesData;
import xenhire.dto.ClientValuesData;
import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CandidateOptions;
import xenhire.model.CandidateQuestionnaire;
import xenhire.model.CandidateValueAssessmentData;
import xenhire.model.ClientAssessmentRanking;
import xenhire.model.ClientOptions;
import xenhire.model.ClientQuestionnaire;
import xenhire.model.ClientValueAssessmentData;
import xenhire.model.Competency;
import xenhire.model.Pillar;
import xenhire.repository.CandidateAssessmentRankingRepository;
import xenhire.repository.CandidateOptionsRepository;
import xenhire.repository.CandidateQuestionnaireRepository;
import xenhire.repository.CandidateValueAssessmentDataRepository;
import xenhire.repository.ClientAssessmentRankingRepository;
import xenhire.repository.ClientOptionsRepository;
import xenhire.repository.ClientQuestionnaireRepository;
import xenhire.repository.ClientValueAssessmentDataRepository;
import xenhire.repository.CompetencyRepository;
import xenhire.repository.PillarRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.loading.MLet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;

@Service
public class UtilityService {
	
	@Autowired
	PillarRepository pillarRepository;
	
	@Autowired
	CompetencyRepository competencyRepository;
	
	@Autowired
	ClientOptionsRepository clientOptionsRepository;
	
	@Autowired
	ClientQuestionnaireRepository clientQuestionnaireRepository;
	
	@Autowired
	CandidateOptionsRepository candidateOptionsRepository;
	
	@Autowired
	CandidateQuestionnaireRepository candidateQuestionnaireRepository;
	
	@Autowired
	ClientAssessmentRankingRepository clientAssessmentRankingRepository;
	
	@Autowired
	CandidateAssessmentRankingRepository candidateAssessmentRankingRepository;
	
	@Autowired
	ClientValueAssessmentDataRepository clientValueAssessmentDataRepository;
	
	@Autowired
	CandidateValueAssessmentDataRepository candidateValueAssessmentDataRepository;
	

	public ResponseEntity<Object> postClientQuestionnaire(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		List<String> data = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        int sno = -1;
        int optionNo = 1;
        int i = 0;
        for (int rowNo = 2; rowNo <= rowCount; rowNo++) {
        	ClientQuestionnaire cq = new ClientQuestionnaire();
        	Row row = sheet.getRow(rowNo);
        	System.out.println(row.getCell(0));
        	
        	Cell cell1 = row.getCell(0);
        	int questionNo = (int) cell1.getNumericCellValue();
        	cq.setQuestionNo(questionNo);
        	
        	Cell cell2 = row.getCell(1);
        	cq.setQuestion(cell2.getStringCellValue());
        	
        	Cell cell6 = row.getCell(5);
        	String questionOptionType = "RATING";
        	if(cell6.getStringCellValue().contains("Ranking") == true) {
                questionOptionType = "RANKING";
            }
        	cq.setOptionType(questionOptionType);
        	
        	cq.setCreatedAt(new Date());
        	
        	cq.setOptionCategory(cell6.getStringCellValue());
        	
        	if(questionNo != sno) {
                sno = questionNo;
                optionNo = 1;
                clientQuestionnaireRepository.save(cq);
            }
        	
        	
        	ClientOptions co = new ClientOptions();
        	
        	Cell cell3 = row.getCell(2);
        	co.setOptionDesc(cell3.getStringCellValue());
        	
        	co.setOptionNo(optionNo);
        	
        	co.setQuestionnaireNo(cq.getQuestionNo());
        	
        	String optionLevel = "N";
        	if(questionOptionType.equals("RANKING") == false) {
        		Cell cell7 = row.getCell(6);
                optionLevel = cell7.getStringCellValue();
            }
        	co.setOptionLevel(optionLevel);
        	
        	Cell cell4 = row.getCell(4);
        	String pillar = cell4.getStringCellValue();
        	System.out.println(pillar);
        	Optional<Pillar> p = pillarRepository.findByName(pillar.trim());
        	
        	Cell cell5 = row.getCell(3);
        	String competency = cell5.getStringCellValue();
        	System.out.println(competency);
        	Competency c = competencyRepository.findByNameAndPillarId(competency.trim(), p.get().getId());
        	co.setCompetencyId(c.getId());
        	
        	co.setCreatedAt(new Date());
        	
        	clientOptionsRepository.save(co);
            
            optionNo ++;
            i++;
        }
        workbook.close();
		return null;
		
	}

	public ResponseEntity<Object> postCandidateQuestionnaire(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		List<String> data = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        int sno = -1;
        int optionNo = 1;
        int i = 0;
        List<Map> ansList = new ArrayList();
        for (int rowNo = 2; rowNo <= rowCount; rowNo++) {
        	CandidateQuestionnaire cq = new CandidateQuestionnaire();
        	Row row = sheet.getRow(rowNo);
        	System.out.println(row.getCell(0));
        	
        	Cell cell0 = row.getCell(0);
        	int questionNo = (int) cell0.getNumericCellValue();
        	cq.setQuestionNo(questionNo);
        	
        	Cell cell1 = row.getCell(1);
        	cq.setQuestion(cell1.getStringCellValue());
        	
        	Cell cell3 = row.getCell(3);
        	cq.setCorrectOption(-1);
        	
        	Cell cell6 = row.getCell(6);
        	String questionOptionType = "RATING";
        	if(cell6.getStringCellValue().contains("Ranking") == true) {
                questionOptionType = "RANKING";
            }
        	cq.setOptionType(questionOptionType);
        	
        	Cell cell7 = row.getCell(7);
        	cq.setSection(cell7.getStringCellValue());
        	
        	cq.setOptionCategory(cell6.getStringCellValue());
        	
        	cq.setCreatedAt(new Date());
        	
        	if(questionNo != sno) {
                sno = questionNo;
                optionNo = 1;
                candidateQuestionnaireRepository.save(cq);
            }
        	
        	
        	CandidateOptions co = new CandidateOptions();
        	
        	Cell cell2 = row.getCell(2);
        	String optionDesc = "";
        	if(questionNo == 53) {
        		optionDesc = cell2.getNumericCellValue() + "";
        	}
        	else {
        		optionDesc = cell2.getStringCellValue();
        	}
        	
        	co.setOptionDesc(optionDesc);
        	
        	co.setOptionNo(optionNo);
        	
        	co.setQuestionnaireNo(cq.getQuestionNo());
        	
        	
        	Cell cell5 = row.getCell(5);
        	String pillar = cell5.getStringCellValue();
        	System.out.println(pillar);
        	Optional<Pillar> p = pillarRepository.findByName(pillar.trim());
        	//System.out.println(p.get().getId());
        	
        	Cell cell4 = row.getCell(4);
        	String competency = cell4.getStringCellValue();
        	System.out.println(competency);
        	Competency c = null;
        	if(p.isEmpty()) {
        		if(questionNo == 84) {
        			co.setCompetencyId(32);
        		}
        		else {
        			c = competencyRepository.findByName(competency.trim());
        			co.setCompetencyId(c.getId());
        		}
        	}
        	else {
        		c = competencyRepository.findByNameAndPillarId(competency.trim(), p.get().getId());
        		co.setCompetencyId(c.getId());
        	}
        	
        	co.setCreatedAt(new Date());
        	
        	candidateOptionsRepository.save(co);
        	
        	
        	if(questionOptionType.equals("RATING") && cell3.getStringCellValue().equals("Correct")) {
        		Map correctAns = new HashMap();
        		correctAns.put(optionDesc, questionNo);
        		ansList.add(correctAns);
        	}
            
            optionNo ++;
            i++;
        }
        workbook.close();
        
        for(Map<String, Integer> m : ansList) {
        	for(String key : m.keySet()) {
        		System.out.println("option = " + key + ", question " + m.get(key));
	        	CandidateOptions option = candidateOptionsRepository.findByQuestionnaireNoAndOptionDesc(m.get(key), key);
	    		CandidateQuestionnaire question = candidateQuestionnaireRepository.findByQuestionNo(option.getQuestionnaireNo());
	    		question.setCorrectOption(option.getId());
	    		candidateQuestionnaireRepository.save(question);
        	}
        }
		
		return null;
	}
	
	
	
	public int getJobMatchingScores(long clientId, long candidateId){
		
		List<ClientAssessmentRanking> clientRankingList = clientAssessmentRankingRepository.findByClientId(clientId);
		List<CandidateAssessmentRanking> candidateRankingList = candidateAssessmentRankingRepository.findByCandidateId(candidateId);
		
		 int clientPillarScore = 0;
         int candidatePillarScore = 0;
		 int candidateOverallScore = 0;
         boolean values = false;
         boolean skillsAndProficiency = false;
         int score = 0;
		for(ClientAssessmentRanking clientRanking : clientRankingList) {
			//System.out.println("clientRanking = " + clientRanking);
			
			for(CandidateAssessmentRanking candidateRanking : candidateRankingList) {
				
//				System.out.println("candidateRanking = " + candidateRanking);
				
				String clientRating = clientRanking.getRating();
                String candidateRating = candidateRanking.getRating();

                if(clientRanking.getPillar().equals("Values") && candidateRanking.getPillar().equals("Values") && values == false) {
                    // if(client.PillarRank <= candidate.PillarRank) score = candidate.PillarScore;
                    // if(client.PillarRank >= candidate.PillarRank) score = client.PillarScore;
                    values = true;
                    clientPillarScore = clientRanking.getPillarScore();
                    candidatePillarScore = candidateRanking.getPillarScore();
                    score = GetValueScore(clientId, candidateId, clientPillarScore, candidatePillarScore);
                    System.out.println("values = " + score);
                }
                if(clientRanking.getPillar().equals("Skills and Proficiency") && candidateRanking.getPillar().equals("Skills and Proficiency") && skillsAndProficiency == false){
                    if(clientRanking.getPillarRank() <= candidateRanking.getPillarRank()) score = candidateRanking.getPillarScore();
                    if(clientRanking.getPillarRank() >= candidateRanking.getPillarRank()) score = clientRanking.getPillarScore();
                    skillsAndProficiency = true;
                    System.out.println("sp = " + score);
                }

                if(clientRanking.getCompetency().isEmpty() == false && clientRanking.getCompetency().equals(candidateRanking.getCompetency()) && clientRanking.getPillar().equals("Values") == false && clientRanking.getPillar().equals("Skills and Proficiency") == false) {
                    score = candidateRanking.getCompetencyScore();
                    if((clientRanking.getRating().equals("High") && candidateRanking.getRating().equals("Med")) || clientRanking.getRating().equals("Med") && candidateRanking.getRating().equals("Low")) {
                        score = (2*candidateRanking.getCompetencyScore())/3;
                        if(clientRanking.getCompetencyRank() < candidateRanking.getCompetencyRank()) score = (2*candidateRanking.getCompetencyScore())/3;
                        if(clientRanking.getCompetencyRank() > candidateRanking.getCompetencyRank()) score = (2*clientRanking.getCompetencyScore())/3;
                    }
                    if(clientRanking.getRating().equals("High") && candidateRanking.getRating().equals("Low")) {
                        score = candidateRanking.getCompetencyScore()/3;
                        if(clientRanking.getCompetencyRank() < candidateRanking.getCompetencyRank()) score = (1*candidateRanking.getCompetencyScore())/3;
                        if(clientRanking.getCompetencyRank() > candidateRanking.getCompetencyRank()) score = (1*clientRanking.getCompetencyScore())/3;
                    }
                    System.out.println("score = " + score);
                }
			}
			System.out.println("overall score = " + candidateOverallScore);
			candidateOverallScore = candidateOverallScore + score;
		}
		
		return candidateOverallScore;
	}

	 public int getStaticScore(int rank) {
         int score = 0;
         if(rank == 1) score = 10;
         if(rank == 2) score = 9;
         if(rank == 3) score = 8;
         if(rank == 4) score = 7;
         if(rank == 5) score = 6;
         if(rank == 6) score = 5;
         if(rank == 7) score = 4;
         if(rank == 8) score = 3;
         if(rank == 9) score = 2;
         if(rank == 10) score = 1;

         return score;
     }

	 
	private int GetValueScore(long clientId, long candidateId, int clientPillarScore, int candidatePillarScore) {
		System.out.println(clientPillarScore + " " + candidatePillarScore);
		// TODO Auto-generated method stub
		 List<String> mostLikely = new ArrayList<String>();
         List<String> leastLikely = new ArrayList<String>();
         List<ClientValuesData> cvdList = new ArrayList<ClientValuesData>();
         List<CandidateValuesData> candidateList = new ArrayList<CandidateValuesData>();
         int valuesScore = 0;
         int staticScore = 0;
         List<String> valuesList = new ArrayList<String>();
         valuesList.add("Self-Direction");
         valuesList.add("Stimulation");
         valuesList.add("Hedonism");
         valuesList.add("Achievement");
         valuesList.add("Power");
         valuesList.add("Security");
         valuesList.add("Conformity");
         valuesList.add("Tradition");
         valuesList.add("Benevolence");
         valuesList.add("Universalism");

         ClientValueAssessmentData cvd = clientValueAssessmentDataRepository.findByClientId(clientId);
         System.out.println(cvd);
         List<CandidateValueAssessmentData> candidateValuesList = candidateValueAssessmentDataRepository.findByCandidateId(candidateId);
         for(CandidateValueAssessmentData cand : candidateValuesList) {
        	 mostLikely.add(cand.getMostLikely());
        	 leastLikely.add(cand.getLeastLikely());
         }
         ClientValuesData selfDirection = new ClientValuesData();
         selfDirection.setRank(cvd.getSelfDirection());
         selfDirection.setValueName("Self-Direction");
         selfDirection.setStaticScore(getStaticScore(cvd.getSelfDirection()));
         staticScore += getStaticScore(cvd.getSelfDirection());
         System.out.println(selfDirection);
         cvdList.add(selfDirection);
         
         ClientValuesData stimulation = new ClientValuesData();
         stimulation.setRank(cvd.getStimulation());
         stimulation.setValueName("Stimulation");
         stimulation.setStaticScore(getStaticScore(cvd.getStimulation()));
         staticScore += getStaticScore(cvd.getStimulation());
         cvdList.add(stimulation);
         
         ClientValuesData achievement = new ClientValuesData();
         achievement.setRank(cvd.getAchievement());
         achievement.setValueName("Achievement");
         achievement.setStaticScore(getStaticScore(cvd.getAchievement()));
         staticScore += getStaticScore(cvd.getAchievement());
         cvdList.add(achievement);
         
         ClientValuesData power = new ClientValuesData();
         power.setRank(cvd.getPower());
         power.setValueName("Power");
         power.setStaticScore(getStaticScore(cvd.getPower()));
         staticScore += getStaticScore(cvd.getPower());
         cvdList.add(power);
         
         ClientValuesData security = new ClientValuesData();
         security.setRank(cvd.getSecurity());
         security.setValueName("Security");
         security.setStaticScore(cvd.getSecurity());
         staticScore += getStaticScore(cvd.getSecurity());
         cvdList.add(security);
         
         ClientValuesData conformity = new ClientValuesData();
         conformity.setRank(cvd.getConformity());
         conformity.setValueName("Conformity");
         conformity.setStaticScore(getStaticScore(cvd.getConformity()));
         staticScore += getStaticScore(cvd.getConformity());
         cvdList.add(conformity);
         
         ClientValuesData tradition = new ClientValuesData();
         tradition.setRank(cvd.getTradition());
         tradition.setValueName("Tradition");
         tradition.setStaticScore(getStaticScore(cvd.getTradition()));
         staticScore += getStaticScore(cvd.getTradition());
         cvdList.add(tradition);
         
         ClientValuesData universalism = new ClientValuesData();
         universalism.setRank(cvd.getUniversalism());
         universalism.setValueName("Universalism");
         universalism.setStaticScore(getStaticScore(cvd.getUniversalism()));
         staticScore += getStaticScore(cvd.getUniversalism());
         cvdList.add(universalism);
         
         ClientValuesData hedonism = new ClientValuesData();
         hedonism.setRank(cvd.getHedonism());
         hedonism.setValueName("Hedonism");
         hedonism.setStaticScore(getStaticScore(cvd.getHedonism()));
         staticScore += getStaticScore(cvd.getHedonism());
         cvdList.add(hedonism);
         
         ClientValuesData benevolence = new ClientValuesData();
         benevolence.setRank(cvd.getBenevolence());
         benevolence.setValueName("Benevolence");
         benevolence.setStaticScore(getStaticScore(cvd.getBenevolence()));
         staticScore += getStaticScore(cvd.getBenevolence());
         cvdList.add(benevolence);
         
         System.out.println(staticScore);
         
         int pillarScore = clientPillarScore;
         if(clientPillarScore > candidatePillarScore) pillarScore = candidatePillarScore;
         int dynamicScoreVariable = pillarScore/staticScore;
         System.out.println(dynamicScoreVariable);
         
         for(ClientValuesData clientValues : cvdList) {
        	 	clientValues.setDynamicScore(clientValues.getStaticScore() * dynamicScoreVariable);
         }

         Map<String, Integer> mostLikelyMap =   mostLikely.stream()
								                 .collect(Collectors.groupingBy(
								                         element -> element,              // Group by the element itself
								                         Collectors.collectingAndThen(    // Collect the elements
								                                 Collectors.counting(),   // Count each group
								                                 Long::intValue           // Convert Long to Integer
								                         )
								                 ));
         Map<String, Integer> leastLikelyMap =   leastLikely.stream()
									                 .collect(Collectors.groupingBy(
									                         element -> element,              // Group by the element itself
									                         Collectors.collectingAndThen(    // Collect the elements
									                                 Collectors.counting(),   // Count each group
									                                 Long::intValue           // Convert Long to Integer
									                         )
									                 ));
         
         Map<String, Integer> countDifferences = new HashMap();
         
         for(String key : mostLikelyMap.keySet()) {
        	if(leastLikelyMap.containsKey(key)) {
        		Integer difference = Math.abs(mostLikelyMap.get(key) - leastLikelyMap.get(key));
        		countDifferences.put(key, difference);
        	}
        	else {
        		countDifferences.put(key, mostLikelyMap.get(key));
        	}
         }
         
         for(String key : leastLikelyMap.keySet()) {
        	 if (countDifferences.containsKey(key) && mostLikelyMap.containsKey(key) == false)
               {
                   countDifferences.put(key, leastLikelyMap.get(key) - (2*leastLikelyMap.get(key)));
               }
         }
         
         for(String value : valuesList){
           if(countDifferences.containsKey(value) == false) {
               countDifferences.put(value, 0);
           }
         }
         
         Map<String, Integer> sortedDifferences = countDifferences.entrySet()
                 .stream()
                 .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                 .collect(Collectors.toMap(
                         Map.Entry::getKey,
                         Map.Entry::getValue,
                         (e1, e2) -> e1,
                         LinkedHashMap::new
                 ));
             
             int candidateRank = 0;
             boolean firstRecord = false;
             int candidateStaticScore = 0;
             int rank = 1;
             int score = 10;
             for(String key : sortedDifferences.keySet())
             {
                 for(ClientValuesData cvd1 : cvdList) {
//                	 System.out.println(key);
//                	 System.out.println(cvd1);
                     CandidateValuesData candidateData = new CandidateValuesData();
                     String clientValuename = cvd1.getValueName();
                     String candidateValuename = key;
                     if(clientValuename.equals(candidateValuename)) {
                         if(firstRecord == false){
                             candidateData.setValueName(key);
                             candidateData.setRank(rank);
                             candidateData.setStaticScore(score);
                             candidateRank = sortedDifferences.get(key);
                             candidateList.add(candidateData);
                         }
                         else{
                             if(sortedDifferences.get(key) < candidateRank){
                                  rank ++;
                                  score --;
                             }
                             candidateData.setValueName(key);
                             candidateData.setRank(rank);
                             candidateData.setStaticScore(score);
                             candidateRank = sortedDifferences.get(key);
                             candidateList.add(candidateData);
                         }
                         firstRecord = true;
                         candidateStaticScore += score;
                     }

                 }
             }
             int candidateDynamicVariable = pillarScore/candidateStaticScore;
             
             for(CandidateValuesData cvd3 : candidateList) {
                 cvd3.setDynamicScore(cvd3.getStaticScore() * candidateDynamicVariable);
             }
             for(ClientValuesData client : cvdList) {
                 for(CandidateValuesData candidate : candidateList){
                     if(client.getValueName().equals(candidate.getValueName())) {
                         if(client.getRank() == candidate.getRank()) valuesScore += client.getDynamicScore();
                         if(client.getRank() < candidate.getRank())  valuesScore += candidate.getDynamicScore();
                         if(client.getRank() > candidate.getRank())  valuesScore += client.getDynamicScore();
                     }
                 }
             }   

         return valuesScore;
	}
	

}

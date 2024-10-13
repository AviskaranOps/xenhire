package xenhire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xenhire.dto.CandidateValuesData;
import xenhire.dto.ClientAssessmentDataResponseDTO;
import xenhire.dto.ClientValuesData;
import xenhire.model.AssessmentOptions;
import xenhire.model.AssessmentQuestions;
import xenhire.model.Assessments;
import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CandidateOptions;
import xenhire.model.CandidateQuestionnaire;
import xenhire.model.CandidateValueAssessmentData;
import xenhire.model.ClientAssessmentRanking;
import xenhire.model.ClientOptions;
import xenhire.model.ClientQuestionnaire;
import xenhire.model.ClientValueAssessmentData;
import xenhire.model.Competency;
import xenhire.model.CompetencyScore;
import xenhire.model.Pillar;
import xenhire.model.PillarScore;
import xenhire.model.User;
import xenhire.repository.AssessmentOptionsRepository;
import xenhire.repository.AssessmentQuestionsRepository;
import xenhire.repository.AssessmentsRepository;
import xenhire.repository.CandidateAssessmentRankingRepository;
import xenhire.repository.CandidateOptionsRepository;
import xenhire.repository.CandidateQuestionnaireRepository;
import xenhire.repository.CandidateValueAssessmentDataRepository;
import xenhire.repository.ClientAssessmentRankingRepository;
import xenhire.repository.ClientIcpTemplateRepository;
import xenhire.repository.ClientOptionsRepository;
import xenhire.repository.ClientQuestionnaireRepository;
import xenhire.repository.ClientValueAssessmentDataRepository;
import xenhire.repository.CompetencyRepository;
import xenhire.repository.CompetencyScoreRepository;
import xenhire.repository.PillarRepository;
import xenhire.repository.PillarScoreRepository;
import xenhire.repository.UserRepository;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
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
	
	
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+<>?";
    private static final String ALL_CHARACTERS = UPPER + LOWER + DIGITS + SPECIAL;
    
    private static final SecureRandom random = new SecureRandom();
    
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
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CompetencyScoreRepository competencyScoreRepository;
	
	@Autowired
	PillarScoreRepository pillarScoreRepository;
	
	@Autowired
	ClientIcpTemplateRepository clientIcpTemplateRepository;
	
	@Autowired
	AssessmentsRepository assessmentsRepository;
	
	@Autowired
	AssessmentQuestionsRepository assessmentQuestionsRepository;
	
	@Autowired
	AssessmentOptionsRepository assessmentOptionsRepository;
	
	

	
	public String getLoggedInUsername(long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();
		Optional<User> userOpt = userRepository.findById(id);
		return userOpt.get().getUsername();
    }
	
	
	public ResponseEntity<Object> postAssessments(MultipartFile file) throws IOException {
		 Workbook workbook = new XSSFWorkbook(file.getInputStream());
	     Sheet sheet = workbook.getSheetAt(0);
	     int rowCount = sheet.getLastRowNum();
	     boolean assessmentInjestion = false;
	     int sNo = 0;
	     AssessmentQuestions questions = null;
	     Assessments asses = null;
	     for(int rowNo = 2; rowNo <= rowCount; rowNo++) {
	    	 Row row = sheet.getRow(rowNo);
	    	 Cell cell1 = row.getCell(0);
	    	 Cell cell2 = row.getCell(1);
	    	 Cell cell3 = row.getCell(2);
	    	 Cell cell4 = row.getCell(3);	
	    	 Cell cell5 = row.getCell(4);
	    	 Cell cell6 = row.getCell(5);
	    	 Cell cell7 = row.getCell(6);
	    	 Cell cell8 = row.getCell(7);
	    	 Cell cell9 = row.getCell(8);
	    	 Cell cell10 = row.getCell(9);
	    	 Cell cell11 = row.getCell(10);
	    	 Cell cell12 = row.getCell(11);
	    	 
	    	 if(cell1 == null) {
	        		break;
	         }
	    	 
	    	 int questionNo = (int) cell1.getNumericCellValue();
	    	 String question = cell3.getStringCellValue();
	    	 String option = cell4.getStringCellValue();
	    	 String competency = cell5.getStringCellValue();
	    	 String pillar = cell6.getStringCellValue();
	    	 String ratingType = cell7.getStringCellValue();
	    	 int rating = (int) cell8.getNumericCellValue();
	    	 float weightage = (float) cell9.getNumericCellValue();
	    	 String scoringType = cell10.getStringCellValue();
	    	 String Dimension = cell11.getStringCellValue();
	    	 String testType = cell12.getStringCellValue();
	    	 if(assessmentInjestion == false) {
		    	 Competency c = competencyRepository.findByName(competency);
		    	 Optional<Pillar> p = pillarRepository.findByName(pillar);
		    	 Pillar p1 = p.get();
		    	 asses = assessmentsRepository.findByName(competency);
		    	 asses.setCompetencyId(c.getId());
		    	 asses.setPillarId(p1.getId());
		    	 asses.setScoringType(scoringType);
		    	 assessmentsRepository.save(asses);
		    	 assessmentInjestion = true;
	    	 }
	    	 if(questionNo != sNo) {
		    	 questions = new AssessmentQuestions();
		    	 questions.setAssessmentId(asses.getId());
		    	 questions.setQuestionNo(questionNo);
		    	 questions.setQuestion(question);
		    	 assessmentQuestionsRepository.save(questions);
		    	 sNo ++;
		     }
	    	 
	    	 AssessmentOptions options = new AssessmentOptions();
	    	 options.setDimension(Dimension);
	    	 options.setOptionDesc(option);
	    	 options.setOptionType(ratingType);
	    	 options.setRating(rating);
	    	 options.setWeightage(weightage);
	    	 options.setQuestionId(questions.getId());
	    	 assessmentOptionsRepository.save(options);
	     }
	     
		
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}
	
	
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
        	System.out.println(p.get());
        	Cell cell5 = row.getCell(3);
        	String competency = cell5.getStringCellValue();
        	System.out.println(competency.trim());
        	Competency c = competencyRepository.findByNameAndPillarId(competency.trim(), p.get().getId());
        	co.setCompetencyId(c.getId());
        	
        	co.setCreatedAt(new Date());
        	Cell cell7 = row.getCell(7);
        	int weightage = 0;
        	String scoringType = "weightage normalise";
        	System.out.println(questionOptionType.contains("Ranking"));
        	if(cell6.getStringCellValue().contains("Ranking")) {
            	co.setWeightage(-1);
            	co.setScoringType("NULL");
        	}
        	else {
        		weightage = (int) row.getCell(7).getNumericCellValue();
        		scoringType = row.getCell(8).getStringCellValue();
            	co.setWeightage(weightage);
            	co.setScoringType(scoringType);
        	}
        	
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
        for (int rowNo = 1; rowNo <= rowCount; rowNo++) {
        	
        	
        	CandidateQuestionnaire cq = new CandidateQuestionnaire();
        	Row row = sheet.getRow(rowNo);
        	System.out.println(row.getCell(0));
        	
        	Cell cell0 = row.getCell(0);
        	if(cell0 == null) {
        		break;
        	}
        	int questionNo = (int) cell0.getNumericCellValue();
        	cq.setQuestionNo(questionNo);
        	
        	Cell cell1 = row.getCell(1);
        	cq.setQuestion(cell1.getStringCellValue());
        	
//        	Cell cell3 = row.getCell(3);
        	cq.setCorrectOption(-1);
        	
        	Cell cell5 = row.getCell(5);
        	String questionOptionType = "RATING";
        	if(cell5.getStringCellValue().contains("Ranking") == true) {
                questionOptionType = "RANKING";
            }
        	cq.setOptionType(questionOptionType);
        	
//        	Cell cell7 = row.getCell(7);
        	cq.setSection("section1");
        	
        	cq.setOptionCategory(cell5.getStringCellValue());
        	
        	cq.setCreatedAt(new Date());
        	
        	if(questionNo != sno) {
                sno = questionNo;
                optionNo = 1;
                candidateQuestionnaireRepository.save(cq);
            }
        	
        	
        	CandidateOptions co = new CandidateOptions();
        	
        	Cell cell2 = row.getCell(2);
        	String optionDesc = "";

        	optionDesc = cell2.getStringCellValue();
        	
        	co.setOptionDesc(optionDesc);
        	
        	co.setOptionNo(optionNo);
        	
        	co.setQuestionnaireNo(cq.getQuestionNo());
        	
        	
        	Cell cell4 = row.getCell(4);
        	String pillar = cell4.getStringCellValue();
        	System.out.println(pillar);
        	Optional<Pillar> p = pillarRepository.findByName(pillar.trim());
        	System.out.println(p.get());
        	
        	Cell cell3 = row.getCell(3);
        	String competency = cell3.getStringCellValue();
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
        	Cell cell7 = row.getCell(7);
        	int weightage = 0;
        	String scoringType = "weightage normalise";
        	System.out.println(questionOptionType.contains("Ranking"));
        	if(cell5.getStringCellValue().contains("Ranking") || (questionNo >= 56 && questionNo <= 61)) {
            	co.setWeightage(-1);
            	co.setScoringType("NULL");
        	}
        	else {
        		weightage = (int) row.getCell(7).getNumericCellValue();
        		scoringType = row.getCell(8).getStringCellValue();
            	co.setWeightage(weightage);
            	co.setScoringType(scoringType);
        	}
        	
        	candidateOptionsRepository.save(co);
        	
        	
        	if((questionNo >= 56 && questionNo <= 61) && cell7.getStringCellValue().equals("Correct")) {
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
	
	
	
	public int getJobMatchingScores(long clientId, long icpId, long valuesId, long candidateId){
		
		List<ClientAssessmentRanking> clientRankingList = clientAssessmentRankingRepository.findByClientIdAndTemplateNo(clientId, icpId);
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
                    score = GetValueScore(clientId, valuesId, candidateId, clientPillarScore, candidatePillarScore);
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

	 
	private int GetValueScore(long clientId, long valuesId, long candidateId, int clientPillarScore, int candidatePillarScore) {
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

         Optional<ClientValueAssessmentData> cvdOpt = clientValueAssessmentDataRepository.findById(valuesId);
         ClientValueAssessmentData cvd = cvdOpt.get();
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
	
	
	
	
	public void extractClientAssessmentScoreAndSave(long clientId, long templateNo, int template) {
		
		System.out.println("client ranking is called = " + clientId);
		
		List<Map<String, Object>> dataList = clientIcpTemplateRepository.getClientAssessment(clientId, templateNo);
		List<ClientAssessmentDataResponseDTO> respList = new ArrayList();
		
		for(Map<String, Object> m : dataList) {
			ClientAssessmentDataResponseDTO data = new ClientAssessmentDataResponseDTO();
			data.setCompetencyId((long) m.get("competency_id"));
			data.setCorrectOption((int) m.get("correct_option"));
			data.setOptionDesc(m.get("option_desc").toString());
			data.setOptionLevel(m.get("option_level").toString());
			data.setOptionNo((long) m.get("option_no"));
			data.setOptionType(m.get("option_type").toString());
			data.setPillar_id((long) m.get("pillar_id"));
			data.setPillarName(m.get("pillarName").toString());
			data.setCompetencyName(m.get("competencyName").toString());
			data.setTemplateName(m.get("template_name").toString());
			data.setWeightage((int) m.get("weightage"));
			data.setCreatedBy(m.get("created_by").toString());
			respList.add(data);
		}
		
		System.out.println("client ranking is called = " + respList);
		
		
		List<ClientAssessmentDataResponseDTO> rankingPillar = respList.stream().filter(card -> card.getOptionType().equals("Ranking_Pillar")).collect(Collectors.toList());
														
		List<ClientAssessmentDataResponseDTO> rankingCompetency = respList.stream().filter(card -> card.getOptionType().equals("Ranking_Competency")).toList(); 
		List<ClientAssessmentDataResponseDTO> rating = respList.stream().filter(card -> card.getOptionType().equals("Rating")).toList(); 
		
		respList.forEach(System.out::println);
//		System.out.println(rankingCompetency);
//		System.out.println(rating);
		 int pillarRank = 1;
		 int competencyRank = 1;
		 String competency = "";
		for(ClientAssessmentDataResponseDTO data1 : rankingPillar) {
			System.out.println("pillar iteration " + pillarRank);
			if(data1.getPillarName().equals("Values") || data1.getPillarName().equals("Skills and Proficiency")) {
				CompetencyScore cs = competencyScoreRepository.findByCompetencyRank(competencyRank);
				PillarScore ps = pillarScoreRepository.findByPillarRank(pillarRank);
				
				ClientAssessmentRanking car = new ClientAssessmentRanking();
				car.setCompetency(data1.getCompetencyName());
				car.setCompetencyRank(competencyRank);
				car.setPillar(data1.getPillarName());
				car.setPillarRank(pillarRank);
				car.setCompetencyScore(cs.getCompetencyScore());
				car.setPillarScore(ps.getPillarScore());
				car.setMaxScore(cs.getCompetencyScore());
				car.setClientId(clientId);
				car.setCreatedAt(LocalDateTime.now());
				car.setRating("Low");
				car.setTemplateNo(templateNo);
				car.setTemplateName(data1.getTemplateName());
				car.setCreatedBy(data1.getCreatedBy());
				car.setTemplate(template == 0 ? false : true);
				clientAssessmentRankingRepository.save(car);
				
				if(pillarRank == 4) { competencyRank ++; }
				else { competencyRank = competencyRank + 4; }
			}
			
			for(ClientAssessmentDataResponseDTO data2 : rankingCompetency) {
				
				if(data2.getPillarName().equals(data1.getPillarName())) {
					System.out.println(data2.getCompetencyName());
					for(ClientAssessmentDataResponseDTO data3 : rating) {
//						System.out.println(data2.getCompetencyName());
//						System.out.println(data3.getCompetencyName());
						if(data2.getCompetencyName().equals(data3.getCompetencyName())) {
							System.out.println(competencyRank);
							CompetencyScore cs = competencyScoreRepository.findByCompetencyRank(competencyRank);
							PillarScore ps = pillarScoreRepository.findByPillarRank(pillarRank);
							ClientAssessmentRanking car = clientAssessmentRankingRepository.findByCompetency(data3.getCompetencyName());
							String rate = data3.getOptionLevel();
							int weightage = data3.getWeightage();
							if(car != null) {
								weightage = (car.getWeightage() + data3.getWeightage()) / (car.getCount() + 1);
								if(weightage > 60) {
									rate = "High";
								}
								if(weightage > 40 && weightage < 60) {
									rate = "Med";
								}
								if(weightage <= 40) {
									rate = "Low";
								}	
							}
							if(car == null) {
								car = new ClientAssessmentRanking();
							}
							
							car.setCompetency(data3.getCompetencyName());
							car.setCompetencyRank(competencyRank);
							car.setPillar(data1.getPillarName());
							car.setPillarRank(pillarRank);
							car.setCompetencyScore(cs.getCompetencyScore());
							car.setPillarScore(ps.getPillarScore());
							car.setMaxScore(cs.getCompetencyScore());
							car.setClientId(clientId);
							car.setCreatedAt(LocalDateTime.now());
							car.setRating(rate);
							car.setTemplateName(data1.getTemplateName());
							car.setCreatedBy(data1.getCreatedBy());
							car.setTemplateNo(templateNo);
							car.setCount(car.getCount() + 1);
							car.setWeightage(data3.getWeightage() + car.getWeightage());
							car.setTemplate(template == 0 ? false : true);
							clientAssessmentRankingRepository.save(car);
						}
					
					}
					
					 if(pillarRank == 5) pillarRank ++;
					 competencyRank ++; 
				}
				
			}
			pillarRank ++;
		}
	}
	
	
	public  String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);

        // Ensure password contains at least one character from each category
        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill the remaining characters with random selections from ALL_CHARACTERS
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // Shuffle the characters in the password to remove any predictable patterns
        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        char[] a = input.toCharArray();
        for (int i = 0; i < a.length; i++) {
            int j = random.nextInt(a.length);
            // Swap characters at positions i and j
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return new String(a);
    }

	
	
	
	

}

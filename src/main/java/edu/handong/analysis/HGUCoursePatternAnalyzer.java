package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysise.utils.NotEnoughArgumentException;
import edu.handong.analysise.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = new HashMap<String,Student>();
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		
		// TODO: Implement this method
		
	      for(String stringFromLine : lines) {
	         Course CourseToAdd = new Course(stringFromLine);
	         String studentIdToCheck = CourseToAdd.getstudentId();
	         
	         //if the student Id already exists in HashMap
	         if(!students.containsKey(studentIdToCheck)) {
	        	 Student studentToAdd = new Student(studentIdToCheck);
	        	 studentToAdd.addCourse(CourseToAdd);
	        	 students.put(studentIdToCheck, studentToAdd);
	         }
	         //if there is no same thing in HashMap 
	         else {
	        	 Student studentInstanceToAddCourse = students.get(studentIdToCheck);
	        	 studentInstanceToAddCourse.addCourse(CourseToAdd);
	        	 students.put(studentIdToCheck, studentInstanceToAddCourse);
	         }
	      }
		
		return students; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semesters in total. In the first semester (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method 
		ArrayList<String> numberOfCoursesTakenInEachSemester = new ArrayList<String>();
		int totalSemester;
		
		for(int  i = 0; i < sortedStudents.size(); i++) {
			String studentIdToCheck = String.format("%04d", i+1);
			Student studentToCheck = sortedStudents.get(studentIdToCheck);
			String studentId = studentToCheck.getStudentId();
			totalSemester = studentToCheck.getSemesterByYearAndSemester().size();
			
			for(int j = 0; j < studentToCheck.getSemesterByYearAndSemester().size(); j++) {
				int numOfCoursesInNthSemester = studentToCheck.getNumCourseNthSemester(j+1);
				String studentIdToAdd = studentId;
				String toAdd = studentIdToAdd + "," + totalSemester + "," + j + "," + numOfCoursesInNthSemester;
				numberOfCoursesTakenInEachSemester.add(toAdd);
			}
		}
		return numberOfCoursesTakenInEachSemester; // do not forget to return a proper variable
	}
}


package my.utem.bitp3123.reporting_service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class ReportingService {

    private final RestTemplate restTemplate = new RestTemplate();
    
    private final String STUDENT_SERVICE_URL = "http://localhost:8080/api/students";
    private final String ENROLMENT_SERVICE_URL = "http://localhost:8081/api/enrolments";

    public Map<String, Long> getEnrolmentCountByProgramme() {
        try {
            // 1. Fetch all students from Student Service
            StudentDTO[] studentsArray = restTemplate.getForObject(STUDENT_SERVICE_URL, StudentDTO[].class);
            List<StudentDTO> students = studentsArray != null ? Arrays.asList(studentsArray) : Collections.emptyList();

            // 2. Fetch all enrollments from Enrolment Service
            EnrolmentDTO[] enrolmentsArray = restTemplate.getForObject(ENROLMENT_SERVICE_URL, EnrolmentDTO[].class);
            List<EnrolmentDTO> enrolments = enrolmentsArray != null ? Arrays.asList(enrolmentsArray) : Collections.emptyList();

            // 3. Collect only the student IDs that have a status of "Success"
            Set<Integer> successfullyEnrolledStudentIds = new HashSet<>();
            for (EnrolmentDTO e : enrolments) {
                if (e != null && "Success".equalsIgnoreCase(e.status())) {
                    successfullyEnrolledStudentIds.add(e.studentId());
                }
            }

            // 4. Count successful enrollments grouped by academic programme
            Map<String, Long> reportMap = new HashMap<>();
            for (StudentDTO student : students) {
                if (student != null && successfullyEnrolledStudentIds.contains(student.id())) {
                    String prog = student.programme();
                    // If the programme isn't in the map yet, start at 0, then add 1
                    reportMap.put(prog, reportMap.getOrDefault(prog, 0L) + 1L);
                }
            }

            return reportMap;

        } catch (Exception e) {
            System.err.println("Aggregation failure: " + e.getMessage());
            return Collections.emptyMap();
        }
    }
}
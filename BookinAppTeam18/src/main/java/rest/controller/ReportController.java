package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.domain.Report;
import rest.service.ReportService;

import java.util.Collection;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Report>> getReports() {
        Collection<Report> reports = reportService.findAll();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> getReport(@PathVariable("id") Long id) {
        Report report = reportService.findOne(id);

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> createReport(@RequestBody Report report) throws Exception {
        Report savedReport = reportService.create(report);
        return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> updateReport(@RequestBody Report report, @PathVariable Long id)
            throws Exception {
        report.setId(id);
        Report updatedReport = reportService.update(report);

        if (updatedReport == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Report> deleteReport(@PathVariable("id") Long id) {
        reportService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

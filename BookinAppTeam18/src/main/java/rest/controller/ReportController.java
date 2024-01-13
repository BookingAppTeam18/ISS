package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.domain.DTO.AccountDTO;
import rest.domain.DTO.ReportDTO;
import rest.service.ReportService;

import java.util.Collection;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    //ALL reports
    @PreAuthorize("hasAnyAuthority('OWNER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReportDTO>> getReports() {
        Collection<ReportDTO> reports = reportService.findAll();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    //report by id
    @PreAuthorize("hasAnyAuthority('OWNER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getReport(@PathVariable("id") Long id) {
        ReportDTO report = reportService.findOne(id);

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    //reports for account
    @PreAuthorize("hasAnyAuthority('OWNER')")
    @GetMapping(value = "account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReportDTO>> getAccountReports(@PathVariable("id") Long accountId) {
        Collection<ReportDTO> report = reportService.findAccountReports(accountId);

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    //reports for comment
    @PreAuthorize("hasAnyAuthority('OWNER')")
    @GetMapping(value = "comment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReportDTO>> getCommentReports(@PathVariable("id") Long accountId) {
        Collection<ReportDTO> report = reportService.findCommentReports(accountId);

        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(value = "accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountDTO>> getAccountsReports() {
        Collection<AccountDTO> report = reportService.findAccountsReports();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    //create report
    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO report) throws Exception {
        ReportDTO savedReport = reportService.insert(report);
        return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
    }

    //update report
    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> updateReport(@RequestBody ReportDTO report, @PathVariable Long id)
            throws Exception {
        report.setId(id);
        ReportDTO updatedReport = reportService.update(report);

        if (updatedReport == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    //delete report
    @PreAuthorize("hasAnyAuthority('OWNER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ReportDTO> deleteReport(@PathVariable("id") Long id) {
        reportService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

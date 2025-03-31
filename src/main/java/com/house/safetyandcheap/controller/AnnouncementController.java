package com.house.safetyandcheap.controller;

import com.house.safetyandcheap.dto.AnnouncementDto;
import com.house.safetyandcheap.dto.search.AnnouncementSearchDto;
import com.house.safetyandcheap.service.AnnouncementService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    @GetMapping("/search")
    public ResponseEntity<List<AnnouncementDto>> search(@RequestBody AnnouncementSearchDto searchDto) {
        log.info("Authentication: {}", SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(announcementService.searchAnnouncements(searchDto));
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AnnouncementDto announcementDto) {
        announcementService.insertAnnouncement(announcementDto);
        return ResponseEntity.ok("Success");
    }
}

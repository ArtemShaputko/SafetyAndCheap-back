package com.house.safetyandcheap.service;

import com.house.safetyandcheap.dto.AnnouncementDto;
import com.house.safetyandcheap.dto.search.AnnouncementSearchDto;
import com.house.safetyandcheap.mapper.Mapper;
import com.house.safetyandcheap.model.User;
import com.house.safetyandcheap.repository.AnnouncementRepository;
import com.house.safetyandcheap.repository.UserRepository;
import com.house.safetyandcheap.specification.AnnouncementSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnnouncementService {
    AnnouncementRepository announcementRepository;
    UserRepository userRepository;
    Mapper mapper;
    public List<AnnouncementDto> searchAnnouncements(AnnouncementSearchDto searchDto) {
        return announcementRepository.findAll(new AnnouncementSpecification(searchDto)).stream().map(
                announcement -> mapper.announcementToDto(announcement)
        ).toList();
    }
    public void insertAnnouncement(AnnouncementDto dto) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
        var announcement = mapper.announcementFromDto(dto);
        announcement.setAuthor(user);
        announcementRepository.save(announcement);
    }
}

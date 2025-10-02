package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.BlacklistQueryDTO;
import com.bughunters.mountainspirit.report.query.mapper.BlacklistQueryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlacklistQueryServiceImplTest {

    @Mock
    private BlacklistQueryMapper blacklistQueryMapper;

    @InjectMocks
    private BlacklistQueryServiceImpl blacklistQueryService;

    @Test
    void testGetAllBlacklist_ReturnsList() {
        // given
        BlacklistQueryDTO bl1 = new BlacklistQueryDTO(1L, LocalDateTime.now().minusDays(10), 1001L, 101L);
        BlacklistQueryDTO bl2 = new BlacklistQueryDTO(2L, LocalDateTime.now().minusDays(5), null, 102L);

        List<BlacklistQueryDTO> blacklist = Arrays.asList(bl1, bl2);

        when(blacklistQueryMapper.selectAllBlacklist()).thenReturn(blacklist);

        // when
        List<BlacklistQueryDTO> result = blacklistQueryService.getAllBlacklist();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals(101L, result.get(0).getMemberId());
        assertEquals(1001L, result.get(0).getAdminId());

        assertEquals(2L, result.get(1).getId());
        assertEquals(102L, result.get(1).getMemberId());
        assertNull(result.get(1).getAdminId());

        verify(blacklistQueryMapper, times(1)).selectAllBlacklist();
    }

    @Test
    void testGetAllBlacklist_EmptyList() {
        when(blacklistQueryMapper.selectAllBlacklist()).thenReturn(List.of());

        List<BlacklistQueryDTO> result = blacklistQueryService.getAllBlacklist();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(blacklistQueryMapper, times(1)).selectAllBlacklist();
    }
}

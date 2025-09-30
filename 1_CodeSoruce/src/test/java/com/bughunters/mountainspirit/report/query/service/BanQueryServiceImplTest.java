package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.BanQueryDTO;
import com.bughunters.mountainspirit.report.query.mapper.BanQueryMapper;
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
class BanQueryServiceImplTest {

    @Mock
    private BanQueryMapper banQueryMapper;

    @InjectMocks
    private BanQueryServiceImpl banQueryService;

    @Test
    void testSelectAllBan_ReturnsBanList() {
        // given
        BanQueryDTO ban1 = new BanQueryDTO(1L, LocalDateTime.now().minusDays(5),
                LocalDateTime.now().plusDays(2), 101L, 1001L);
        BanQueryDTO ban2 = new BanQueryDTO(2L, LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(4), 102L, null);

        List<BanQueryDTO> banList = Arrays.asList(ban1, ban2);

        when(banQueryMapper.selectAllBan()).thenReturn(banList);

        // when
        List<BanQueryDTO> result = banQueryService.selectAllBan();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals(101L, result.get(0).getUserId());
        assertEquals(1001L, result.get(0).getAdminId());

        assertEquals(2L, result.get(1).getId());
        assertEquals(102L, result.get(1).getUserId());
        assertNull(result.get(1).getAdminId());

        verify(banQueryMapper, times(1)).selectAllBan();
    }

    @Test
    void testSelectAllBan_EmptyList() {
        when(banQueryMapper.selectAllBan()).thenReturn(List.of());

        List<BanQueryDTO> result = banQueryService.selectAllBan();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(banQueryMapper, times(1)).selectAllBan();
    }
}

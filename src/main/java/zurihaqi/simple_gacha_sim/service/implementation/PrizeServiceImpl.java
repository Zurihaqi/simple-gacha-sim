package zurihaqi.simple_gacha_sim.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;
import zurihaqi.simple_gacha_sim.model.Prize;
import zurihaqi.simple_gacha_sim.repository.PrizeRepository;
import zurihaqi.simple_gacha_sim.repository.TierRepository;
import zurihaqi.simple_gacha_sim.service.PrizeService;
import zurihaqi.simple_gacha_sim.utils.ObjectMapper;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrizeServiceImpl implements PrizeService {
    private final PrizeRepository prizeRepository;
    private final TierRepository tierRepository;

    @Override
    public Page<PrizeDTO> getAllPrizes(Pageable pageable) {
        List<PrizeDTO> mappedUser = prizeRepository.findAll(pageable).stream().map(ObjectMapper::toPrizeDTO).toList();
        return new PageImpl<>(mappedUser, pageable, prizeRepository.count());
    }

    @Override
    public PrizeDTO getOnePrize(Long id) {
        return ObjectMapper.toPrizeDTO(prizeRepository.findById(id).orElseThrow(() -> new RuntimeException("Prize not found")));
    }

    @Override
    @Transactional
    public PrizeDTO createPrize(PrizeDTO prizeDto) {
        if(prizeDto.getName() == null) throw new RuntimeException("Prize name cannot be null");
        if(prizeDto.getTierId() == null) throw new RuntimeException("Prize tier id cannot be null");
        if(prizeDto.getDescription() == null) throw new RuntimeException("Prize description cannot be null");
        if(prizeDto.getImageUrl() == null) throw new RuntimeException("Prize image url cannot be null");

        Prize newPrize = new Prize();
        newPrize.setName(prizeDto.getName());
        newPrize.setDescription(prizeDto.getDescription());
        newPrize.setImageUrl(prizeDto.getImageUrl());
        newPrize.setCreatedAt(new Date());
        newPrize.setUpdatedAt(null);
        newPrize.setTier(tierRepository.findById(prizeDto.getTierId()).orElseThrow(() -> new RuntimeException("Tier not found")));

        return ObjectMapper.toPrizeDTO(prizeRepository.save(newPrize));
    }

    @Override
    @Transactional
    public PrizeDTO updatePrize(Long id, PrizeDTO prizeDto) {
        Prize newPrize = prizeRepository.findById(id).orElseThrow(() -> new RuntimeException("Prize not found"));
        if(prizeDto.getName() != null) newPrize.setName(prizeDto.getName());
        if(prizeDto.getDescription() != null) newPrize.setDescription(prizeDto.getDescription());
        if(prizeDto.getImageUrl() != null) newPrize.setImageUrl(prizeDto.getImageUrl());
        newPrize.setUpdatedAt(new Date());

        return ObjectMapper.toPrizeDTO(prizeRepository.save(newPrize));
    }

    @Override
    @Transactional
    public void deletePrize(Long id) {
        prizeRepository.findById(id).orElseThrow(() -> new RuntimeException("Prize not found"));
        prizeRepository.deleteById(id);
    }
}

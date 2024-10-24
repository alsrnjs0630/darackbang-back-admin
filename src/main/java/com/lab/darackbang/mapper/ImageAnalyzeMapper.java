package com.lab.darackbang.mapper;

import com.lab.darackbang.dto.analyze.ImageAnalyzeDTO;
import com.lab.darackbang.entity.ImageAnalyze;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageAnalyzeMapper {

    ImageAnalyzeDTO toDTO( ImageAnalyze imageAnalyze);

    ImageAnalyze toEntity(ImageAnalyzeDTO imageAnalyzeDTO);
}

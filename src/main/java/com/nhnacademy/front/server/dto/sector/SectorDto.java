package com.nhnacademy.front.server.dto.sector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectorDto {

  private Long sectorId;
  private String sectorName;
  private int totalCount;
  private int normalCount;
  private int isOnCount;

}

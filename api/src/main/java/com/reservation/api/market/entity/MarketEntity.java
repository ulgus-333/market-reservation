package com.reservation.api.market.entity;

import com.reservation.api.common.entity.meta.BaseExecutorEntity;
import com.reservation.api.utils.DatabaseUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(catalog = DatabaseUtils.MARKET_SCHEMA, name = "market")
@Entity
public class MarketEntity extends BaseExecutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String identificationNumber;

    @Column(nullable = false, length = 50)
    private String representativeName;

    @Column(nullable = false, length = 50)
    private String representativePhone;

    @Column(nullable = false, length = 2)
    private String regionCode;

    @Column(nullable = false, length = 3)
    private String cityDistrictCode;

    @Column(nullable = false, length = 3)
    private String townCode;

    @Column(nullable = false, length = 2)
    private String villageCode;

    @Column(nullable = false)
    private String landAddress;

    private String roadAddress;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public static MarketEntity console() {
        return MarketEntity.builder()
                .idx(0L)
                .build();
    }

    public MarketEntity(String name, String identificationNumber, String representativeName, String representativePhone, String regionCode, String cityDistrictCode, String townCode, String villageCode, String landAddress, String roadAddress, Double latitude, Double longitude, LocalDateTime regDatetime, Long regIdx) {
        this(null, name, identificationNumber, representativeName, representativePhone, regionCode, cityDistrictCode, townCode, villageCode, landAddress, roadAddress, latitude, longitude, regDatetime, regIdx);
    }

    @Builder(access = AccessLevel.PRIVATE)
    public MarketEntity(Long idx, String name, String identificationNumber, String representativeName, String representativePhone, String regionCode, String cityDistrictCode, String townCode, String villageCode, String landAddress, String roadAddress, Double latitude, Double longitude, LocalDateTime regDatetime, Long regIdx) {
        super(regDatetime, regIdx);
        this.idx = idx;
        this.name = name;
        this.identificationNumber = identificationNumber;
        this.representativeName = representativeName;
        this.representativePhone = representativePhone;
        this.regionCode = regionCode;
        this.cityDistrictCode = cityDistrictCode;
        this.townCode = townCode;
        this.villageCode = villageCode;
        this.landAddress = landAddress;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

package br.com.devjmcn.backend_projeto_temperatura.model.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data")
public class DataEntity {
    @Id
    @GeneratedValue
    UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private UnitEntity unit;

    @Column(name = "ref_min")
    double refMin;
    @Column(name = "ref_cur")
    double refCur;
    @Column(name = "ref_max")
    double refMax;
    @Column(name = "env_min")
    double envMin;
    @Column(name = "env_cur")
    double envCur;
    @Column(name = "env_max")
    double envMax;
    @Column(name = "date_time")
    Long dateTime;

    public DataEntity(
            @NotNull UserEntity userId,
            @NotNull UnitEntity unitId,
            @NotNull double refMin,
            @NotNull double refCur,
            @NotNull double refMax,
            @NotNull double envMin,
            @NotNull double envCur,
            @NotNull double envMax,
            @NotNull long dateTime) {
        this.user = userId;
        this.unit = unitId;
        this.refMin = refMin;
        this.refCur = refCur;
        this.refMax = refMax;
        this.envMin = envMin;
        this.envCur = envCur;
        this.envMax = envMax;
        this.dateTime = dateTime;
    }
}

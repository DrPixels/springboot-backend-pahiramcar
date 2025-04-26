package com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("DAMAGE_REPAIR_FEE")
public class DamageRepairFeeTransaction extends Transaction {

    @Column(nullable = false)
    private String carDamageDescription;

    @OneToMany(
            mappedBy = "damageRepairFeeTransaction"
    )
    @JsonManagedReference
    private List<Image> carDamageImages;
}

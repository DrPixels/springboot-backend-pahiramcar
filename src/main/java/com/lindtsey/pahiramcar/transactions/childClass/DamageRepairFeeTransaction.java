package com.lindtsey.pahiramcar.transactions.childClass;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("DAMAGE_REPAIR_FEE")
public class DamageRepairFeeTransaction extends Transaction {

    private String carDamageDescription;

    @OneToMany(
            mappedBy = "damageRepairFeeTransaction"
    )
    @JsonManagedReference
    private List<Image> carDamageImages;
}

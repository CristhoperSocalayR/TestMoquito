package pe.edu.vallegrande.SupplierNPH.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("shed")
public class Shed {

    @Id
    private Long id;

    private String name;

    private String location;

    private Integer capacity;

    @Column("chicken_type")
    private String chickenType;

    @Column("inspection_date")
    private LocalDate inspectionDate;

    private String note;

    private String status = "A";

    @Column("supplier_id") // Relación con la tabla supplier
    private Long supplierId;
}

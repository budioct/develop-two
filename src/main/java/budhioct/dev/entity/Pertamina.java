package budhioct.dev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
@Entity
@Table(name = "pertamina")
public class Pertamina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pertamina_group_affiliate")
    private String pertaminaGroupAffiliate;
    @Column(name = "address")
    private String address;
    @Column(name = "contact")
    private String contact;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "pertamina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfficialAgent> officialAgents;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_stock", referencedColumnName = "id")
    private Stock stock;

    public Pertamina(String pertaminaGroupAffiliate, String address, String contact, List<OfficialAgent> officialAgents, Stock stock) {
        this.pertaminaGroupAffiliate = pertaminaGroupAffiliate;
        this.address = address;
        this.contact = contact;
        this.officialAgents = officialAgents;
        this.stock = stock;
    }

}

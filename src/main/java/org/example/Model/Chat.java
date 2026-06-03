package org.example.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class Chat {
    @Id
    private final String id;
    private final String name;
    private final Set<String> participantIds;
}

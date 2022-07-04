package net.mungai.idonor.app.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false, unique = true)
    private String name;

     public Role(){}

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (id == null){
            return other.id == null;
        }else return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}


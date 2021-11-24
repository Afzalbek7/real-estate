package maroqand.uz.real_estate.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.List;

@Entity(name = "announcement")
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "create_on")
    private Date createOn;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fileStorage_id", referencedColumnName = "id")
//    private FileStorage fileStorage;

    @ManyToMany
    @JoinTable(
            name = "announcement_file",
            joinColumns = {
                    @JoinColumn(name = "announcement_id", referencedColumnName = "id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "file_storage_id", referencedColumnName = "id")
            }
    )
    private List<FileStorage> fileStorageList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public List<FileStorage> getFileStorageList() {
        return fileStorageList;
    }

    public void setFileStorageList(List<FileStorage> fileStorageList) {
        this.fileStorageList = fileStorageList;
    }
}

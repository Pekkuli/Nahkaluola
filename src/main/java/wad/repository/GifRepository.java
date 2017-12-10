
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.file.FileObject;


public interface GifRepository  extends JpaRepository<FileObject, Long> {
}

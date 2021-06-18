package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.dao.TagDao;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.service.impl.TagServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

	TagServiceImpl tagService;
	
	@Mock TagDao tagDao;
	
	@BeforeEach
	public void init() {
		
		Tag tag = new Tag().setNome("TagTest");
		
		Mockito.lenient().when(tagDao.findByNome("TagTest")).thenReturn(tag);
		
		tagService = new TagServiceImpl(tagDao);
	}
	
	@Test
	@DisplayName("TagServiceImpl - getTagByNomeTest")
	void getTagByNomeTest() {
		
		TagDTO tagdto = tagService.getTagByNome("TagTest");
		
		assertAll(
				() -> assertNotNull(tagdto, "Tag should not be null"),
				() -> assertEquals(tagdto.getNome(), "TagTest", "Tag name should be 'TagTest'")
				);
		
		
		assertThrows(NullPointerException.class, () -> tagService.getTagByNome("wrong"), "Non exsting tag should throw an exception.");

		
	}
}

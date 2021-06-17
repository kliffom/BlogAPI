package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.rdev.blog.api.dao.entity.User;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TestErrorAndFailure {
	
	@Test
	void testUserSet() {
		User u = new User();
		u.setUsername("");
		assertTrue(u.getUsername() == null, () -> "il valore restituito da getUsername() dovrebbe essere null");
		
		u.setUsername("ddinuzzo");
		assertEquals("ddinuzzo",
				u.getUsername(),
				() -> "il valore restituito da getUsername() dovrebbe essere ddinuzzo");
	}

	@Test
	@Disabled
	void notEmpty() {
		String str = "";
		assertFalse(str.isEmpty());
	}

	@SuppressWarnings("null")
	@Test
	@Disabled
	void throwsException() {
		String str = null;
		assertTrue(str.length() > 10);
	}
}

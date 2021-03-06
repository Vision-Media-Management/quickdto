package com.github.quickdto.testharness;

import static org.junit.Assert.*;

import com.github.quickdto.testharness.SimpleDto.Fields;
import com.github.quickdto.testharness.impl.Simple;
import org.junit.Test;


public class SimpleDtoTest {

	@Test
	public void testMethodCreation() throws Exception {

		// Normal
		SimpleDto.class.getDeclaredMethod("getNormal");
		SimpleDto.class.getDeclaredMethod("setNormal", int.class);

		// ReadOnly
		SimpleDto.class.getDeclaredMethod("getReadOnly");
		try {
			SimpleDto.class.getDeclaredMethod("setReadOnly", int.class);
		    fail();
		} catch (NoSuchMethodException ignored) {}

		// ReadOnlyWithSetter
		SimpleDto.class.getDeclaredMethod("getReadOnlyWithSetter");
		SimpleDto.class.getDeclaredMethod("setReadOnlyWithSetter", int.class);

	}

	@Test
	public void testCopy() {
		Simple from = new Simple();
		from.setNormal(1);
		from.setReadOnly(1);
		from.setReadOnlyWithSetter(1);

		SimpleDto dto = new SimpleDto();

		dto.copyFrom(from);
		assertFalse(dto.isDirty());
		assertEquals(1, dto.getReadOnly());
		assertEquals(1, dto.getReadOnly());
		assertEquals(1, dto.getReadOnly());

		Simple toClean = new Simple();
		assertEquals(0, toClean.getNormal());
		assertEquals(0, toClean.getReadOnly());
		assertEquals(0, toClean.getReadOnlyWithSetter());

		dto.setNormal(2);
		dto.setReadOnlyWithSetter(2);
		dto.setDirty(Fields.READ_ONLY, true);

		Simple toDirty = new Simple();
		dto.copyTo(toDirty);
		assertEquals(2, toDirty.getNormal());
		assertEquals(0, toDirty.getReadOnly());
		assertEquals(0, toDirty.getReadOnlyWithSetter());

	}
}

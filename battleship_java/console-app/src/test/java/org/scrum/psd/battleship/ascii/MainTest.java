package org.scrum.psd.battleship.ascii;

import org.junit.Assert;
import org.junit.Test;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;

public class MainTest {
    @Test
    public void testParsePosition() {
        Position actual = Main.parsePosition("A1");
        Position expected = new Position(Letter.A, 1);

        Assert.assertEquals(expected, actual);
    }
}

/*
 * Joda Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2004 Stephen Colebourne.  
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Joda project (http://www.joda.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Joda" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact licence@joda.org.
 *
 * 5. Products derived from this software may not be called "Joda",
 *    nor may "Joda" appear in their name, without prior written
 *    permission of the Joda project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE JODA AUTHORS OR THE PROJECT
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Joda project and was originally 
 * created by Stephen Colebourne <scolebourne@joda.org>. For more
 * information on the Joda project, please see <http://www.joda.org/>.
 */
package org.joda.time;

import java.util.Locale;
import java.util.TimeZone;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class is a JUnit test for MutableDuration.
 *
 * @author Stephen Colebourne
 */
public class TestMutablePeriod_Updates extends TestCase {
    // Test in 2002/03 as time zones are more well known
    // (before the late 90's they were all over the place)

    private static final DateTimeZone PARIS = DateTimeZone.getInstance("Europe/Paris");
    private static final DateTimeZone LONDON = DateTimeZone.getInstance("Europe/London");
    
    long y2002days = 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 
                     366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 
                     365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 +
                     366 + 365;
    long y2003days = 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 
                     366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 
                     365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 +
                     366 + 365 + 365;
    
    // 2002-06-09
    private long TEST_TIME_NOW =
            (y2002days + 31L + 28L + 31L + 30L + 31L + 9L -1L) * DateTimeConstants.MILLIS_PER_DAY;
            
    // 2002-04-05
    private long TEST_TIME1 =
            (y2002days + 31L + 28L + 31L + 5L -1L) * DateTimeConstants.MILLIS_PER_DAY
            + 12L * DateTimeConstants.MILLIS_PER_HOUR
            + 24L * DateTimeConstants.MILLIS_PER_MINUTE;
        
    // 2003-05-06
    private long TEST_TIME2 =
            (y2003days + 31L + 28L + 31L + 30L + 6L -1L) * DateTimeConstants.MILLIS_PER_DAY
            + 14L * DateTimeConstants.MILLIS_PER_HOUR
            + 28L * DateTimeConstants.MILLIS_PER_MINUTE;
    
    private DateTimeZone originalDateTimeZone = null;
    private TimeZone originalTimeZone = null;
    private Locale originalLocale = null;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestMutablePeriod_Updates.class);
    }

    public TestMutablePeriod_Updates(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(TEST_TIME_NOW);
        originalDateTimeZone = DateTimeZone.getDefault();
        originalTimeZone = TimeZone.getDefault();
        originalLocale = Locale.getDefault();
        DateTimeZone.setDefault(LONDON);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        Locale.setDefault(Locale.UK);
    }

    protected void tearDown() throws Exception {
        DateTimeUtils.setCurrentMillisSystem();
        DateTimeZone.setDefault(originalDateTimeZone);
        TimeZone.setDefault(originalTimeZone);
        Locale.setDefault(originalLocale);
        originalDateTimeZone = null;
        originalTimeZone = null;
        originalLocale = null;
    }

    //-----------------------------------------------------------------------
    public void testTest() {
        assertEquals("2002-06-09T00:00:00.000Z", new Instant(TEST_TIME_NOW).toString());
        assertEquals("2002-04-05T12:24:00.000Z", new Instant(TEST_TIME1).toString());
        assertEquals("2003-05-06T14:28:00.000Z", new Instant(TEST_TIME2).toString());
    }

    //-----------------------------------------------------------------------
    public void testAddYears() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addYears(10);
        assertEquals(11, test.getYears());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addYears(-10);
        assertEquals(-9, test.getYears());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addYears(0);
        assertEquals(1, test.getYears());
    }

    //-----------------------------------------------------------------------
    public void testAddMonths() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMonths(10);
        assertEquals(12, test.getMonths());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMonths(-10);
        assertEquals(-8, test.getMonths());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMonths(0);
        assertEquals(2, test.getMonths());
    }

    //-----------------------------------------------------------------------
    public void testAddWeeks() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addWeeks(10);
        assertEquals(13, test.getWeeks());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addWeeks(-10);
        assertEquals(-7, test.getWeeks());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addWeeks(0);
        assertEquals(3, test.getWeeks());
    }

    //-----------------------------------------------------------------------
    public void testAddDays() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addDays(10);
        assertEquals(14, test.getDays());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addDays(-10);
        assertEquals(-6, test.getDays());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addDays(0);
        assertEquals(4, test.getDays());
    }

    //-----------------------------------------------------------------------
    public void testAddHours() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addHours(10);
        assertEquals(15, test.getHours());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addHours(-10);
        assertEquals(-5, test.getHours());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addHours(0);
        assertEquals(5, test.getHours());
    }

    //-----------------------------------------------------------------------
    public void testAddMinutes() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMinutes(10);
        assertEquals(16, test.getMinutes());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMinutes(-10);
        assertEquals(-4, test.getMinutes());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMinutes(0);
        assertEquals(6, test.getMinutes());
    }

    //-----------------------------------------------------------------------
    public void testAddSeconds() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addSeconds(10);
        assertEquals(17, test.getSeconds());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addSeconds(-10);
        assertEquals(-3, test.getSeconds());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addSeconds(0);
        assertEquals(7, test.getSeconds());
    }

    //-----------------------------------------------------------------------
    public void testAddMillis() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMillis(10);
        assertEquals(18, test.getMillis());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMillis(-10);
        assertEquals(-2, test.getMillis());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMillis(0);
        assertEquals(8, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetYears() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setYears(10);
        assertEquals(10, test.getYears());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setYears(-10);
        assertEquals(-10, test.getYears());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setYears(0);
        assertEquals(0, test.getYears());
    }

    //-----------------------------------------------------------------------
    public void testSetMonths() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMonths(10);
        assertEquals(10, test.getMonths());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMonths(-10);
        assertEquals(-10, test.getMonths());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMonths(0);
        assertEquals(0, test.getMonths());
    }

    //-----------------------------------------------------------------------
    public void testSetWeeks() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setWeeks(10);
        assertEquals(10, test.getWeeks());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setWeeks(-10);
        assertEquals(-10, test.getWeeks());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setWeeks(0);
        assertEquals(0, test.getWeeks());
    }

    //-----------------------------------------------------------------------
    public void testSetDays() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setDays(10);
        assertEquals(10, test.getDays());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setDays(-10);
        assertEquals(-10, test.getDays());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setDays(0);
        assertEquals(0, test.getDays());
    }

    //-----------------------------------------------------------------------
    public void testSetHours() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setHours(10);
        assertEquals(10, test.getHours());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setHours(-10);
        assertEquals(-10, test.getHours());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setHours(0);
        assertEquals(0, test.getHours());
    }

    //-----------------------------------------------------------------------
    public void testSetMinutes() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMinutes(10);
        assertEquals(10, test.getMinutes());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMinutes(-10);
        assertEquals(-10, test.getMinutes());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMinutes(0);
        assertEquals(0, test.getMinutes());
    }

    //-----------------------------------------------------------------------
    public void testSetSeconds() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setSeconds(10);
        assertEquals(10, test.getSeconds());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setSeconds(-10);
        assertEquals(-10, test.getSeconds());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setSeconds(0);
        assertEquals(0, test.getSeconds());
    }

    //-----------------------------------------------------------------------
    public void testSetMillis() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMillis(10);
        assertEquals(10, test.getMillis());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMillis(-10);
        assertEquals(-10, test.getMillis());
        
        test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMillis(0);
        assertEquals(0, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetTimePeriod_8ints1() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setTimePeriod(11, 12, 13, 14, 15, 16, 17, 18);
        assertEquals(11, test.getYears());
        assertEquals(12, test.getMonths());
        assertEquals(13, test.getWeeks());
        assertEquals(14, test.getDays());
        assertEquals(15, test.getHours());
        assertEquals(16, test.getMinutes());
        assertEquals(17, test.getSeconds());
        assertEquals(18, test.getMillis());
    }

    public void testSetTimePeriod_8ints2() {
        MutableTimePeriod test = new MutableTimePeriod(100L, DurationType.getMillisType());
        try {
            test.setTimePeriod(11, 12, 13, 14, 15, 16, 17, 18);
            fail();
        } catch (IllegalArgumentException ex) {}
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100, test.toDurationMillis());
    }

    public void testSetTimePeriod_8ints3() {
        MutableTimePeriod test = new MutableTimePeriod(100L, DurationType.getMillisType());
        test.setTimePeriod(0, 0, 0, 0, 0, 0, 0, 18);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(18, test.getMillis());
        assertEquals(18, test.toDurationMillis());
    }

    public void testSetTimePeriod_8ints4() {
        MutableTimePeriod test = new MutableTimePeriod(0, 0, 0, 0, 5, 6, 7, 8);
        assertEquals(true, test.isPrecise());
        test.setTimePeriod(11, 12, 13, 14, 15, 16, 17, 18);
        assertEquals(false, test.isPrecise());
        assertEquals(11, test.getYears());
        assertEquals(12, test.getMonths());
        assertEquals(13, test.getWeeks());
        assertEquals(14, test.getDays());
        assertEquals(15, test.getHours());
        assertEquals(16, test.getMinutes());
        assertEquals(17, test.getSeconds());
        assertEquals(18, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetTimePeriod_RP1() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setTimePeriod(new MutableTimePeriod(11, 12, 13, 14, 15, 16, 17, 18));
        assertEquals(11, test.getYears());
        assertEquals(12, test.getMonths());
        assertEquals(13, test.getWeeks());
        assertEquals(14, test.getDays());
        assertEquals(15, test.getHours());
        assertEquals(16, test.getMinutes());
        assertEquals(17, test.getSeconds());
        assertEquals(18, test.getMillis());
    }

    public void testSetTimePeriod_RP2() {
        MutableTimePeriod test = new MutableTimePeriod(100L, DurationType.getMillisType());
        try {
            test.setTimePeriod(new MutableTimePeriod(11, 12, 13, 14, 15, 16, 17, 18));
            fail();
        } catch (IllegalArgumentException ex) {}
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100, test.toDurationMillis());
    }

    public void testSetTimePeriod_RP3() {
        MutableTimePeriod test = new MutableTimePeriod(100L, DurationType.getMillisType());
        test.setTimePeriod(new MutableTimePeriod(0, 0, 0, 0, 0, 0, 0, 18));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(18, test.getMillis());
        assertEquals(18, test.toDurationMillis());
    }

    public void testSetTimePeriod_RP4() {
        MutableTimePeriod test = new MutableTimePeriod(0, 0, 0, 0, 5, 6, 7, 8);
        assertEquals(true, test.isPrecise());
        test.setTimePeriod(new MutableTimePeriod(11, 12, 13, 14, 15, 16, 17, 18));
        assertEquals(false, test.isPrecise());
        assertEquals(11, test.getYears());
        assertEquals(12, test.getMonths());
        assertEquals(13, test.getWeeks());
        assertEquals(14, test.getDays());
        assertEquals(15, test.getHours());
        assertEquals(16, test.getMinutes());
        assertEquals(17, test.getSeconds());
        assertEquals(18, test.getMillis());
    }

    public void testSetTimePeriod_RP5() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setTimePeriod((ReadableTimePeriod) null);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetTimePeriod_long_long1() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetTimePeriod_long_long2() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt2.getMillis(), dt1.getMillis());
        assertEquals(-1, test.getYears());
        assertEquals(-1, test.getMonths());
        assertEquals(-1, test.getWeeks());
        assertEquals(-1, test.getDays());
        assertEquals(-1, test.getHours());
        assertEquals(-1, test.getMinutes());
        assertEquals(-1, test.getSeconds());
        assertEquals(-1, test.getMillis());
    }

    public void testSetTimePeriod_long_long3() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        test.setTimePeriod(dt1.getMillis(), dt1.getMillis());
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    public void testSetTimePeriod_long_long_NoYears() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getAllType().withYearsRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(0, test.getYears());
        assertEquals(13, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetTimePeriod_long_long_NoMonths() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getAllType().withMonthsRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(5, test.getWeeks());
        assertEquals(3, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetTimePeriod_long_long_NoWeeks() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getAllType().withWeeksRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(8, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetTimePeriod_long_long_NoDays() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getAllType().withDaysRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(25, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetTimePeriod_long_long_NoHours() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getAllType().withHoursRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(61, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetTimePeriod_long_long_NoMinutes() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getAllType().withMinutesRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(61, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetTimePeriod_long_long_NoSeconds() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getAllType().withSecondsRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(1001, test.getMillis());
    }

    public void testSetTimePeriod_long_long_NoMillis() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getAllType().withMillisRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setTimePeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetTimePeriod_long1() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setTimePeriod(100L);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
    }

    public void testSetTimePeriod_long2() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearMonthType());
        test.setTimePeriod(
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    public void testSetTimePeriod_long3() {
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearWeekType());
        test.setTimePeriod(
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L);
        assertEquals(1, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(12, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    public void testSetTimePeriod_long_NoYears() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearMonthType().withYearsRemoved());
        test.setTimePeriod(ms);
        assertEquals(0, test.getYears());
        assertEquals(15, test.getMonths()); // totalDays=365+85=450=15*30
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetTimePeriod_long_NoMonths() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearMonthType().withMonthsRemoved());
        test.setTimePeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(85, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetTimePeriod_long_NoWeeks() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearWeekType().withWeeksRemoved());
        test.setTimePeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(85, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetTimePeriod_long_NoDays() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearMonthType().withDaysRemoved());
        test.setTimePeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(5 + 25 * 24, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetTimePeriod_long_NoHours() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearMonthType().withHoursRemoved());
        test.setTimePeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(6 + 5 * 60, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetTimePeriod_long_NoMinutes() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearMonthType().withMinutesRemoved());
        test.setTimePeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(7 + 6 * 60, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetTimePeriod_long_NoSeconds() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearMonthType().withSecondsRemoved());
        test.setTimePeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(8 + 7 * 1000, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetTimePeriod_long_NoMillis() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutableTimePeriod test = new MutableTimePeriod(DurationType.getPreciseYearMonthType().withMillisRemoved());
        test.setTimePeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(0, test.getMillis());
        assertEquals(ms - 8, test.toDurationMillis());
    }

    //-----------------------------------------------------------------------
    public void testAdd_long1() {
        MutableTimePeriod test = new MutableTimePeriod(100L);
        test.add(100L);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(200, test.getMillis());
        assertEquals(200L, test.toDurationMillis());
    }

    public void testAdd_long2() {
        MutableTimePeriod test = new MutableTimePeriod(100L, DurationType.getPreciseYearMonthType());
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        test.add(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(108, test.getMillis());
        assertEquals(ms + 100L, test.toDurationMillis());
    }

    public void testAdd_long3() {
        MutableTimePeriod test = new MutableTimePeriod(100L, DurationType.getPreciseYearMonthType());
        long ms =0L;
        test.add(ms);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100L, test.toDurationMillis());
    }

    public void testAdd_long4() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        try {
            test.add(100L);
            fail();
        } catch (IllegalStateException ex) {}
    }

    //-----------------------------------------------------------------------
    public void testAdd_RP1() {
        MutableTimePeriod test = new MutableTimePeriod(100L);
        test.add(new TimePeriod(100L));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(200, test.getMillis());
        assertEquals(200L, test.toDurationMillis());
    }

    public void testAdd_RP2() {
        MutableTimePeriod test = new MutableTimePeriod(100L, DurationType.getPreciseYearMonthType());
        test.add(new TimePeriod(1, 0, 12, 1, 5, 6, 7, 8, DurationType.getPreciseYearWeekType()));
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(108, test.getMillis());
    }

    public void testAdd_RP3() {
        MutableTimePeriod test = new MutableTimePeriod(100L, DurationType.getPreciseYearMonthType());
        test.add(new TimePeriod(0L));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100L, test.toDurationMillis());
    }

    public void testAdd_RP4() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        try {
            test.add(new TimePeriod(100L));
            fail();
        } catch (IllegalStateException ex) {}
    }

    public void testAdd_RP5() {
        MutableTimePeriod test = new MutableTimePeriod(100L);
        try {
            test.add(new TimePeriod(1, 2, 3, 4, 5, 6, 7, 8));
            fail();
        } catch (IllegalStateException ex) {}
    }

    public void testAdd_RP6() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.add((ReadableTimePeriod) null);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(3, test.getWeeks());
        assertEquals(4, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testNormalize1() {
        MutableTimePeriod test = new MutableTimePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        try {
            test.normalize();
            fail();
        } catch (IllegalStateException ex) {}
    }

    public void testNormalize2() {
        MutableTimePeriod test = new MutableTimePeriod(1, 14, 0, 36, 29, 66, 67, 1008, DurationType.getPreciseYearMonthType());
        //   365 + 14*30 + 6 days
        // extra year created from 12 months of 30 days plus 5 extra days
        // 2*365 +  2*30 + 1 day
        test.normalize();
        assertEquals(2, test.getYears());
        assertEquals(3, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(2, test.getDays());
        assertEquals(6, test.getHours());
        assertEquals(7, test.getMinutes());
        assertEquals(8, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

}
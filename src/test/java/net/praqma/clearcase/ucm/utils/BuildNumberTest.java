package net.praqma.clearcase.ucm.utils;

import static org.junit.Assert.*;

import java.io.File;


import net.praqma.clearcase.ucm.UCMException;
import net.praqma.clearcase.ucm.entities.Baseline;
import net.praqma.clearcase.ucm.entities.Component;
import net.praqma.clearcase.ucm.entities.Project;
import net.praqma.clearcase.ucm.entities.UCM;
import net.praqma.clearcase.ucm.entities.UCMEntity;
import net.praqma.util.debug.Logger;
import net.praqma.util.structure.Tuple;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BuildNumberTest
{
	private static Logger logger = Logger.getLogger( false );
	
	@BeforeClass
	public static void startup()
	{
		UCM.SetContext( UCM.ContextType.CLEARTOOL );
	}
	
	
	
	@Test
	public void testCreateBuildNumber() throws UCMException
	{		
		Component component = UCMEntity.GetComponent( "System@\\Cool_PVOB" );
		File view = new File( "c:\\" );
		Tuple<Baseline, String[]> result = BuildNumber.createBuildNumber( "bls__1_2_3_123", component, view );
		
		assertEquals( "Major", result.t2[0], "1" );
		assertEquals( "Minor", result.t2[1], "2" );
		assertEquals( "Patch", result.t2[2], "3" );
		assertEquals( "Sequence", result.t2[3], "123" );
		
		assertEquals( "Baseline", result.t1.GetFQName(), "baseline:bls__1_2_3_123@\\Cool_PVOB" );
		
	}

	
	@Test
	public void testStampFromComponent() throws UCMException
	{
		Component component = UCMEntity.GetComponent( "System@\\Cool_PVOB" );
		File view = new File( "c:\\" );
		
		Tuple<Baseline, String[]> result = BuildNumber.createBuildNumber( "bls__1_2_3_123", component, view );
		
		BuildNumber.stampFromComponent( component, view, result.t2[0], result.t2[1], result.t2[2], result.t2[3] );
	}

	@Test
	public void testStampIntoCode()
	{
		assertTrue( true );
	}
	

	@Test
	public void testGetNextBuildSequence() throws UCMException
	{
		Project project = UCMEntity.GetProject( "project:bn_project@\\Cool_PVOB", true );
		
		int seq = BuildNumber.getNextBuildSequence( project );
		
		assertTrue( seq == 1235 );
	}
	
	@Test
	public void testGetNextBuildSequenceNoSequence() throws UCMException
	{
		Project project = UCMEntity.GetProject( "project:bn_project_no@\\Cool_PVOB", true );
		
		try
		{
			BuildNumber.getNextBuildSequence( project );
		}
		catch( UCMException e )
		{
			assertTrue( true );
		}
	}

	@Test
	public void testGetBuildNumber() throws UCMException
	{
		Project project = UCMEntity.GetProject( "project:bn_project@\\Cool_PVOB", true );
		
		String bn = BuildNumber.getBuildNumber( project );
		
		assertEquals( "__1_2_3_1235", bn );
		
	}

}

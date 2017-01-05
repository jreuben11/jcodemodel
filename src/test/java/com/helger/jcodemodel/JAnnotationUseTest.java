/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 * Portions Copyright 2013-2017 Philip Helger + contributors
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.helger.jcodemodel;

import java.lang.annotation.Inherited;

import org.junit.Assert;
import org.junit.Test;

import com.helger.jcodemodel.util.CodeModelTestsHelper;

/**
 * Unit test for class {@link JAnnotationUse}.
 *
 * @author Philip Helger
 */
public final class JAnnotationUseTest
{
  @Test
  public void generatesGenericParam () throws JClassAlreadyExistsException
  {
    final JCodeModel codeModel = new JCodeModel ();
    final JDefinedClass testClass = codeModel._class ("Test");
    final JAnnotationUse suppressWarningAnnotation = testClass.annotate (SuppressWarnings.class);
    suppressWarningAnnotation.param (JAnnotationUse.SPECIAL_KEY_VALUE, "unused");

    Assert.assertEquals ("@java.lang.SuppressWarnings(\"unused\")",
                         CodeModelTestsHelper.generate (suppressWarningAnnotation));
  }

  @Test
  public void generatesGenericParam2 () throws JClassAlreadyExistsException
  {
    final JCodeModel codeModel = new JCodeModel ();
    final JDefinedClass testClass = codeModel._class ("Test");
    final JAnnotationUse suppressWarningAnnotation = testClass.annotate (SuppressWarnings.class);
    suppressWarningAnnotation.paramArray (JAnnotationUse.SPECIAL_KEY_VALUE, "unused", "deprecation");

    final String sCRLF = System.getProperty ("line.separator");
    Assert.assertEquals ("@java.lang.SuppressWarnings({" +
                         sCRLF +
                         "    \"unused\"," +
                         sCRLF +
                         "    \"deprecation\"" +
                         sCRLF +
                         "})",
                         CodeModelTestsHelper.generate (suppressWarningAnnotation));
  }

  @Test
  public void testOnMethodAndField () throws Exception
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass cls = cm._class ("Test");
    final JMethod m = cls.method (JMod.PUBLIC, cm.VOID, "foo");
    m.annotate (Deprecated.class);

    final JFieldVar field = cls.field (JMod.PRIVATE, cm.DOUBLE, "y");
    field.annotate (Deprecated.class);

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  @Test
  public void testPackageAnnotation () throws Exception
  {
    final JCodeModel cm = new JCodeModel ();
    cm._package ("foo").annotate (Inherited.class);
    CodeModelTestsHelper.parseCodeModel (cm);
  }
}

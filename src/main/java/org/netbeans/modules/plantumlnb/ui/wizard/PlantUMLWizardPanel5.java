/* 
 * The MIT License
 *
 * Copyright 2017 Venkat Ram Akkineni.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.netbeans.modules.plantumlnb.ui.wizard;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.plantumlnb.StringUtils;
import org.netbeans.modules.plantumlnb.Utils;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;

public class PlantUMLWizardPanel5 implements WizardDescriptor.Panel<WizardDescriptor>, ValidatingWizardPanel,
        ChangeListener, ErrorNotifiable {

    /**
     * The visual component that displays this panel. If you need to access the component from this class, just use
     * getComponent().
     */
    private PlantUMLVisualPanel5 component;

    private WizardDescriptor wizard;

    private final ChangeSupport changeSupport = new ChangeSupport(this);

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public PlantUMLVisualPanel5 getComponent() {
        if (component == null) {
            component = PlantUMLVisualPanel5.createInstance(this);
            component.addChangeListener(this);
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        String displayNameRegex = component.getDisplayNameRegex();
        
        if (StringUtils.isEmpty(displayNameRegex)) {
            setErrorMessage(null);
            return true;
        } else {
            if (!Utils.isRegexPattern(displayNameRegex)) {
                setErrorMessage("PlantUMLVisualPanel5.displayNameTextExpressionArea.errorText");
                return false;
            }
            setErrorMessage(null);
            return true;
        }
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
        this.wizard = wiz;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
    }

    @Override
    public void validate() throws WizardValidationException {
        component.validate();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        fireChangeEvent();
    }

    @Override
    public ChangeSupport getChangeSupport() {
        return changeSupport;
    }

    public WizardDescriptor getWizard() {
        return wizard;
    }
}

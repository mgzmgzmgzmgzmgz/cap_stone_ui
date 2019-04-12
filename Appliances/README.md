The contents of this folder are working classes for handling details for all appliances
within the household. The Class hierarchy is as follows:

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
-Appliance // A generalized class for basic details of all appliances
    -Electronics // A Master class for appliances using only electrity         
        -Electronic                       
        -SwitchedElectronic   
    -WaterAppliances //Master class for all appliances that use water and/or electricity         
        -WaterAppliance //Use only water                 
        -HybridAppliance //Use both water and electricity                     
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


**IMPORTANT**: The Appliance folder needs to be copied in its entirety. The internal
folder "Data" has all the info needed to build the master classes. Without it, there
will be errors and the classes won't know what you're talking about. 

Using the classes: When you make an Electronics object, for example, you can use its
functions to get what you need. It can give you the object itself for direct control,
but it can also find the info you need for you. Any of these getters only requires
that you give the Master a name, then it gets what you need. Same goes for any object
of the WaterAppliances class.
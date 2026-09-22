**Use Case:** Run Weekly Accounting Procedures

**Context:** At midnight on Friday, the main accounting procedure is run at the ChocAn Data Center. It reads the week’s file of services provided and prints a number of reports. Each report also can be run individually at the request of a ChocAn manager at any time during the week.

**Actors:** Manager, Time Clock 

**Main Success Scenario:**
    1. Trigger occur each Friday at midnight, or when a ChocAn manager requested.
    2. The system reads the week’s file of services provided.
    3. Include Use Case Generate Member Reports.
    4. Include Use Case Generate Provider Reports.
    5. Include Use Case Generate Summary Report.
    6. Include Use Case Generate EFT Data Record.
    
**Extensions:**
    1a. If a manager requests a single select report rather than a full report, then the system runs the corresponding use case report.
    2a. If no services were recorded the entire week, then the system generates reports indicating zero activities during the week.

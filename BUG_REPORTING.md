Bug Reporting
=============

This document serves as a reference to how bug reporting must/will be done in AFK Arena. This is to ensure that bugs are fixed efficiently, without wasting time interpereting ambiguous or otherwise badly written bug reports. Please read this entire document before filing a bug report.

Guidelines
----------

1. Be precise and descriptive. For example "Camera flip" is not a good bug report title. It does not give an indication of when the camera flips, and what it means for the camera to flip. Maybe it means the camera gee vir jou a vinger, and maybe it happens when the camera is pointing at your mom. A better title would be "Camera movement breaks when facing directly up or down." as it describes when the problem occurs, and that the problem is with camera movement. Further details should be written in the description.
2. State all the facts. Write down everything you KNOW about the problem. BUT make sure the facts are true before stating them.
3. Provide step-by-step instructions on how to reproduce the bug. In order for the developers to confirm the validity of the bug report, the bug must be reproducable on the developer's machine.
4. Provide console output, stack traces and/or log file contents. The more information available to the developers, the easier it is to fix a bug. Information of this nature should be provided via pastebin (http://pastebin.com/), unless it is only a few lines.
5. A picture paints a thousand words. Similarly, a video provides roughly twenty-five thousand words per second. That's only 25KxiBb/s, so it's not that impressive. But nevertheless, provide screenshots or videos if the problem is a visual artifact or bug.
6. Indicate specific areas that are affected (were applicable). For example, if the problem is regarding camera movement, then the issue is probably with the graphics engine. This helps assigning the correct developer or developer team to the task, and thus makes bug fixing go faster.
7. Last, but not least, please make sure to use correct grammar, spelling and punctuation. This makes the bug report easier to read, and ensures that your point comes across clearly.

Tips
----

1. If you have a hunch, share it with the developers. BUT make sure you indicate that it is only a hunch. For example "I think it may be caused by the overloading of the createBlockingSerializableZergling(int,Object,Runnable,JScrollBar) method in the PersistenceEntityManagerFactoryHelper class, but I can't be entirely sure because the stack trace runs on for 130+ lines." is a good way of writing it down. Of course with those method names, there's bound to be a bug somewhere.
2. Don't be rude. We're all human beings here. The more we get along, the more exciting development we can get done.
3. Make sure you're working with the latest revision. It could be possible that the bug you are experiencing has already been fixed, and you're just living in the past.
4. Try reproduce the bug on another machine or operating system, maybe the problem is on your side.

Bug report template
-------------------

Title: <Short, but descriptive title>

Description:
<Describe the problem in detail, including all the facts, relative media and links to pastebins>

Affects Area:
<List, in bullet form, the area(s) in which the bug is, or most likely is located. This section can be removed if this is unclear>

Steps to Reproduce:
<Provide step-by-step instruction describing exactly what to do to cause the bug to occur>

Conclusion
----------

It's really not that hard to write a good bug report. Use your head, and we all have a nice day. Don't follow these guidelines and you're gonna have a bad time. You WILL be asked to revise your bug report if it is not up to scratch. We're going to be strict about this. I'm very OCD about bug reports!

Sincerely,
Daniel "jellymann" Smith, the code monkey

P.S. This bug report guidelines document is subject to change. If you have any issues with the document in its current form, feel free to file a bug report detailing your problem with it. :P

## Summary
From batch mode, SBT incremental compilation unconditionally invalidates a macro class if the class doesn't have a companion object.
Note that this behavior does **not** occur in interactive mode.

## Steps

```
git clone git@github.com:jackkoenig/sbt-macro-dependency.git
cd sbt-macro-dependency
sbt compile
sbt compile
```

## Problem

On the second `sbt compile`, the following debug message will be printed:
```
Invalidating /scratch/koenig/sbt-macro-dependency/macros/target/scala-2.12/classes/mymacros/MyMacro.class: could not find class mymacros.MyMacro$ on the classpath.
```
And then the "main" project is recompiled because it depends on `MyMacro.class`

## Expectation

Since nothing has changed, nothing should recompile.

## Notes

If you run in interactive mode and comile twice, it doesn't recompile anything (as expected).    
If you add a companion object for `MyMacro`, it doesn't recompile anything (as expected), even in batch mode as in the issue above.

## Versions

SBT Version: 1.1.1    
Scala Version: 2.12.4 (also 2.11.12)

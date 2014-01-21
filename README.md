## Background ##

This project stemmed from ongoing debate between its authors (Christopher
Hoffman and Robert Nagle) about the trade-offs in development speed vs
execution speed for dynamic vs static languages.

Chris, primarily from a game development background, favored staticly-typed
languages like C and C++ because of the execution speed they provided.

Rob, primarily from a web application background, favored dynamically-typed
languages like Python and Clojure because of development speed it allowed.


## The Question ##

The question that led to this particular project was this:
Could Clojure achieve execution speeds approaching those of C? 
If so, might then Clojure offer one of the best combinations of development 
AND execution speed available today?

Rob argued yes to both questions. Chris was skeptical of both.


## The Task ##

The task chosen to test the execution speed of the languages was using Newton's
Method to approximate the roots of a given function to a given tolerance 10,000
times.


## The Results (Part One) ##

The initial versions of the programs, created by Chris, written particular
regard to speed, found that Clojure was indeed dramtically slower by default. 

Code: newton-chris.c, newton-chris.clj

Run timings: c-runs-chris.txt, clojure-runs-chris.txt

Results: C (~0.5ms) vs Clojure (~500ms), Clojure:C ~1000:1

Holy CPU caches, Batman! That's a huge difference. Without optimization,
Clojure is indeed much slower at a mathematical task like this one!


## The Results (Part Two) ##

But the story doesn't end there. (Rob wouldn't allow it to end there.)

Clojure has multiple techniques available to it to dramatically improve
its execution speed. These are applied step-by-step in newton-rob-all.clj

Code: newton-rob.c, newton-rob-fastest.clj

Run timings: c-runs-rob.txt, clojure-runs-fastest.clj

Results: C (~50ms) vs Clojure (~70ms), Clojure:C ~1.2:1

Well, that's a lot closer! With all optimizations applied Clojure appears to
get surprisingly close to C's speed.


## Conclusion ##

So, can Clojure approach C execution speeds? 
In this case, it appears the answer is yes.

So, then, does Clojure offer one of the best combinations of (potential)
development and execution speeds?
That's ultimately a matter of opinion, but the bevy of Clojure projects
on my Github profile will tell you where I come down on that issue ;)

  - Rob

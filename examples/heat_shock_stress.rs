# Heat shock RS with stress
# @input nostress stress 
# @output hse hsp hsf hsf3 mfp hsf3:hse prot hsp:mfp hsp:hsf
# @initial hse prot hsp:hsf stress
# Only reachable graphs are constructed

hsf, hsp, hsf3
hsf hsp mfp, , hsf3
hsf3, hse hsp, hsf
hsf3 hsp mfp, hse, hsf
hsf3 hse, hsp, hsf3:hse
hsf3 hse hsp mfp, , hsf3:hse
hse, hsf3, hse
hse hsf3 hsp, mfp, hse
hsf3:hse, hsp, hsf3:hse hsp
hsf3:hse hsp mfp, , hsf3:hse hsp

hsp hsf, mfp, hsp:hsf
hsp:hsf stress, nostress, hsp hsf
hsp:hsf nostress, stress, hsp:hsf
hsp hsf3, mfp, hsp:hsf
hsp hsf3:hse, mfp, hsp:hsf hse
prot stress, nostress, prot mfp
prot nostress, stress, prot
hsp mfp, , hsp:mfp
mfp, hsp, mfp
hsp:mfp, , hsp prot

---
#hse prot hsp:hsf stress
stress
stress
stress
stress
stress

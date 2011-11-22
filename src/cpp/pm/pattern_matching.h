#ifndef PATTERN_MATCHING_H_INCLUDED
#define PATTERN_MATCHING_H_INCLUDED

#include "match_receivers.h"

namespace PatternMatching
{

/// \struct CharTraits
/// represents traits of the char/wchar_t elements
///
template<typename CharType>
struct CharTraits
{
    static bool is_any_subsequence( CharType ct )
    {
        return ct == '*';
    }

    static bool is_any_element( CharType ct )
    {
        return ct == '?';
    }

    static bool is_equal( CharType lhs, CharType rhs )
    {
        return lhs == rhs;
    }
};

///
/// matches given pattern sequence with the source sequence using
/// `ElementTraits' to identify whether certain pattern symbol is equal
/// to the certain element(s) of the source sequence
///
template<
        typename ElementTraits,     // source&pattern element's traits. For char/wchar_t types
                                    // CharTraits class could be used
        typename SourceIterator,    // source iterator
        typename PatternIterator,   // pattern iterator
        typename MatchReceiver      // matches receiver like FakeMatchReceiver>
        >
static bool match(
             SourceIterator src_begin, SourceIterator src_end,
             PatternIterator pat_begin, PatternIterator pat_end,
             MatchReceiver& match_receiver
             )
{
    PatternIterator     match_pos = pat_end;

    // this variables used for match_receiver in order to
    // provide matched subsequence in the source sequence given
    SourceIterator      src_subsequence_begin   = src_end;
    SourceIterator      src_subsequence_end     = src_end;

    for( ;; )
    {
        // 1st case: check when come to an end of the source sequence given
        if( src_begin == src_end )
        {
            // inform match_receiver about matched subsequence if any
            if( src_subsequence_end != src_end && src_subsequence_begin != src_end )
                match_receiver.match_found( src_subsequence_begin, src_subsequence_end );

            // only one case is acceptable: `any subsequence' symbol at the end of the pattern given
            if( pat_begin != pat_end )
            {
                bool    any_sub_at_last = false; // indicates if pattern have symbol `any subsequence' at its tail
                for( ; pat_begin != pat_end; ++ pat_begin )
                {
                    if( !ElementTraits::is_any_subsequence(*pat_begin) )
                        return false;

                    any_sub_at_last = true;
                }

                // inform match_receiver about matched subsequence
                // it will be empty here, because src_begin == src_end
                if( any_sub_at_last )
                    match_receiver.match_found( src_begin, src_end );
            }

            return true;
        }

        // 2nd case: check when come to an end of the pattern sequence given
        if( pat_begin == pat_end )
        {
            // check whether match_pos is not empty
            // restore at previous matching pos and try again
            if( match_pos != pat_end )
            {
                pat_begin = match_pos;
                ++ src_begin;
                continue;
            }

            // check the end of the string
            if( src_begin == src_end )
            {
                // inform match_receiver about previous matched subsequence if any
                if( src_subsequence_end != src_end && src_subsequence_begin != src_end )
                    match_receiver.match_found( src_subsequence_begin, src_subsequence_end );

                return true;
            }

            return false;
        }

        // 3rd case: check whether current pattern and source symbols is equal to each other
        if( ElementTraits::is_equal(*src_begin, *pat_begin) )
        {
            src_subsequence_end = src_begin;

            ++ src_begin;
            ++ pat_begin;
            continue;
        }

        // 4th case: check whether current pattern's symbol stands for any element in the source sequence
        if( ElementTraits::is_any_element(*pat_begin) )
        {
            match_receiver.match_found( *src_begin );

            // increase counters
            ++ src_begin;
            ++ pat_begin;
            continue;
        }

        // 5th case: check whether current pattern's symbol stands for any subsequence in the source sequence
        if( ElementTraits::is_any_subsequence(*pat_begin) )
        {
            match_pos = ++ pat_begin;

            // inform match_receiver about previously matched subsequence if any
            if( src_subsequence_end != src_end && src_subsequence_begin != src_end )
            {
                match_receiver.match_found( src_subsequence_begin, src_subsequence_end );

                src_subsequence_end = src_end;
            }

            // if any subsequence symbol met at the end of the pattern given
            if( match_pos == pat_end )
            {
                match_receiver.match_found( src_begin, src_end );
                return true;
            }

            // mark source subsequence start
            src_subsequence_begin = src_begin;

            continue;
        } // if any substring met

        // 6th case: check whether it is possible to re-match source subsequence
        if( match_pos != pat_end )
        {
            // reset marker of the subsequence's end
            src_subsequence_end = src_end;

            pat_begin = match_pos;
            ++ src_begin;
            continue;
        }

        // 7th case: the pattern sequence does not match the source sequence
        break;
    } // for( ;; )
    return false;
} // match


static inline bool do_match( const std::string& src, const std::string& pattern )
{
    FakeMatchReceiver   fake_receiver;
    return match< CharTraits<char> >(
                    src.begin(), src.end(),
                    pattern.begin(), pattern.end(),
                    fake_receiver
                    );
}


template<typename ConcreteMatchReceiver>
static inline bool do_match( const char * source, const char * pattern, ConcreteMatchReceiver& match_receiver )
{
    return match< CharTraits<char> >(
                    source, source + strlen(source),
                    pattern, pattern + strlen(pattern),
                    match_receiver
                    );
}



} // namespace PatternMatching

#endif // PATTERN_MATCHING_H_INCLUDED



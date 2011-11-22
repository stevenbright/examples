#include <iostream>
#include <string>

#include <boost/spirit.hpp>
#include <boost/spirit/dynamic/stored_rule.hpp>

using namespace boost;
using namespace std;

// help to build a pattern-matching rule
template<typename PatternMatchingRule>
struct pm_helper
    : spirit::grammar< pm_helper<PatternMatchingRule> >
{
    typedef typename pm_helper<PatternMatchingRule>     self_type;

    PatternMatchingRule &   m_rule;
    bool                    m_star_prev;

    pm_helper( PatternMatchingRule & pm_rule )
        : m_rule( pm_rule )
        , m_star_prev( false )
    {
        // first rule is `fake`
        m_rule = !spirit::nothing_p;
    }

    void operator()( const char * begin, const char * end ) const
    {
        //cout << "s = " << string( begin, end ) << "\n";
        const_cast<self_type*>( this )->accept( begin, end );
    }

    template<typename ScannerT>
    struct definition
    {
        definition( const self_type& self )
        {
            m_pattern = +((+(spirit::graph_p - '*')) | '*')[self];
        }

        const spirit::rule<ScannerT>& start() const
        {
            return m_pattern;
        }

    private:
        spirit::rule<ScannerT>      m_pattern;
    };

private:
    void accept( const char * begin, const char * end )
    {
        // checking *
        char ch = *begin;
        if( ch == '*' )
        {
            // checking whether the substring marker is the last one
            if( *end == 0 )
                m_rule = m_rule.copy() >> (*spirit::alpha_p);

            m_star_prev = true;
            return;
        }

        // text
        if( m_star_prev )
        {
            // *
            m_rule = m_rule.copy() >> (*(spirit::alpha_p - spirit::chseq_p(begin, end)));
            // text
            m_rule = m_rule.copy() >> (spirit::chseq_p(begin, end));
        }
        else
        {
            // text
            m_rule = m_rule.copy() >> (spirit::chseq_p(begin, end));
        }

        m_star_prev = false;
    }
};

struct pm_grammar
    : spirit::grammar<pm_grammar>
{
    string      m_pattern;

    pm_grammar( const string& pattern )
        : m_pattern( pattern )
    {}

    template<typename ScannerT>
    struct definition
    {
        definition( const pm_grammar& self )
        {
            pm_helper< typename spirit::stored_rule<typename ScannerT> > helper_grammar( m_sequence );
            spirit::parse_info<> info = boost::spirit::parse(
                                                                             self.m_pattern.c_str(),
                                                                                             helper_grammar,
                                                                                                             spirit::nothing_p );
            if( !info.full )
                throw std::logic_error( "an error found in the pattern given" );

            return;
        }

        const spirit::stored_rule<ScannerT>& start() const
        {
            return m_sequence;
        }

    private:
        spirit::stored_rule<ScannerT>   m_sequence;
    }; // struct definition
}; // struct pm_grammar

static bool match( const char * source, const char * pattern )
{
    pm_grammar  grammar( pattern );
    spirit::parse_info<> info = parse( source, grammar, spirit::space_p );
    return info.full;
}

static void test_pattern_matching_using_spirit()
{
    if( match("this is a string", "t*is*ing") )
    {
        cout << "Pattern matches the source\n";
    }
    else
    {
        cout << "Pattern does not match to the source\n";
    }
}


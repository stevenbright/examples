#ifndef MATCH_RECEIVERS_H_INCLUDED
#define MATCH_RECEIVERS_H_INCLUDED

#include <iterator>
#include <sstream>

namespace PatternMatching
{


/// \struct FakeMatchReceiver
/// represents a fake receiver of the coincidences found during pattern matching
///
struct FakeMatchReceiver
{
    FakeMatchReceiver()
    {}
    
    template<typename SourceElement>
    static void match_found( SourceElement e ) {}

    template<typename SourceIterator>
    static void match_found( SourceIterator begin, SourceIterator end ) {}
};

/// \class LogMatchReceiver
/// logs matches and serializes it to the output string stream
///
class LogMatchReceiver
{
public:
    LogMatchReceiver()
        : m_match_index( 0 )
        , m_at_least_one_found( false )
    {
        m_ostr << "Matches found in order of an appearance in the pattern given:\n";
    }

    template<typename ValueType>
    void match_found( ValueType value )
    {
        m_at_least_one_found = true;
        m_ostr << ++ m_match_index << ") ? = " << value << "\n";
    }

    template<typename SourceIterator>
    void match_found( SourceIterator begin, SourceIterator end )
    {
        m_at_least_one_found = true;
        m_ostr << ++ m_match_index << ") * = ";
        typedef typename std::iterator_traits<SourceIterator>::value_type ValueType; 
        std::copy( begin, end, std::ostream_iterator<ValueType>(m_ostr, "") );
        m_ostr << "\n";
    }

    std::string report() const
    {
        if( m_at_least_one_found )
            return m_ostr.str();

        return std::string();
    }

private:
    size_t                  m_match_index;
    bool                    m_at_least_one_found;
    std::ostringstream      m_ostr;
};


} // namespace PatternMatching

#endif // MATCH_RECEIVERS_H_INCLUDED

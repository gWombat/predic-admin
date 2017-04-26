package fr.gwombat.predicadmin.web.transformer;

import fr.gwombat.predicadmin.model.entities.Publisher;
import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.vo.AddressVO;
import fr.gwombat.predicadmin.web.vo.ContactDetailVO;
import fr.gwombat.predicadmin.web.vo.PublisherVO;
import fr.gwombat.predicadmin.web.vo.builder.PublisherVoBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class PublisherTransformer extends AbstractEntityTransformer<Publisher, PublisherForm, PublisherVO> {

    private AddressTransformer       addressTransformer;
    private ContactDetailTransformer contactDetailTransformer;

    @Autowired
    public PublisherTransformer(final MessageSource messageSource) {
        super(messageSource);
        this.addressTransformer = new AddressTransformer(messageSource);
        this.contactDetailTransformer = new ContactDetailTransformer(messageSource);
    }

    @Override
    public Publisher toEntity(PublisherForm publisherForm, Publisher publisher) {
        if(publisher == null)
            publisher = new Publisher();

        if(publisherForm != null) {
            publisher.setName(StringUtils.upperCase(publisherForm.getName()));
            publisher.setFirstName(WordUtils.capitalizeFully(publisherForm.getFirstName()));
            publisher.setGender(publisherForm.getGender());

            publisher.setBaptismDate(formatDate(publisherForm.getBaptismDate()));
            publisher.setBirthDate(formatDate(publisherForm.getBirthDate()));

            publisher.setAddress(addressTransformer.toEntity(publisherForm.getAddress(), publisher.getAddress()));
            publisher.setContactDetail(contactDetailTransformer.toEntity(publisherForm.getContactDetail(), publisher.getContactDetail()));
        }

        return publisher;
    }

    @Override
    public PublisherForm toFormObject(Publisher publisher) {
        if(publisher != null) {
            final PublisherForm publisherForm = new PublisherForm();

            publisherForm.setBaptismDate(formatDate(publisher.getBaptismDate()));
            publisherForm.setBirthDate(formatDate(publisher.getBirthDate()));

            publisherForm.setFirstName(publisher.getFirstName());
            publisherForm.setName(publisher.getName());
            publisherForm.setGender(publisher.getGender());
            publisherForm.setIdentifier(publisher.getIdentifier());
            publisherForm.setFullName(publisher.getFullName());

            publisherForm.setAddress(addressTransformer.toFormObject(publisher.getAddress()));
            publisherForm.setContactDetail(contactDetailTransformer.toFormObject(publisher.getContactDetail()));

            return publisherForm;
        }
        return null;
    }

    @Override
    public PublisherVO toViewObject(Publisher publisher) {
        if(publisher != null) {
            final AddressVO addressVo = addressTransformer.toViewObject(publisher.getAddress());
            final ContactDetailVO contactDetailVo = contactDetailTransformer.toViewObject(publisher.getContactDetail());

            final PublisherVoBuilder builder = PublisherVoBuilder.create(contactDetailVo, addressVo)
                                                                 .fullName(publisher.getFullName())
                                                                 .identifier(publisher.getIdentifier())
                                                                 .birthDate(publisher.getBirthDate())
                                                                 .name(publisher.getName())
                                                                 .firstName(publisher.getFirstName())
                                                                 .baptismDate(publisher.getBaptismDate());

            return builder.build();
        }
        return null;
    }

}
